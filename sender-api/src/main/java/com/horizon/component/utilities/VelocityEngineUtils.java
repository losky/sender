package com.horizon.component.utilities;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for working with a VelocityEngine.
 * Provides convenience methods to merge a Velocity template with a model.
 *
 * @author ZhenZhong
 * @date 2016 /7/5
 */
public abstract class VelocityEngineUtils {
    private static final Logger logger = LoggerFactory.getLogger(VelocityEngineUtils.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * Gets resource loader.
     *
     * @return the resource loader
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * Sets resource loader.
     *
     * @param resourceLoader the resource loader
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        VelocityEngineUtils.resourceLoader = resourceLoader;
    }

    /**
     * Merge the specified Velocity template with the given model and write the result
     * to the given Writer.
     *
     * @param velocityEngine   VelocityEngine to work with
     * @param templateLocation the location of template, relative to Velocity's resource loader path
     * @param encoding         the encoding of the template file
     * @param model            the Map that contains model names as keys and model objects as values
     * @param writer           the Writer to write the result to
     * @throws VelocityException if the template wasn't found or rendering failed
     */
    public static void mergeTemplate(
            VelocityEngine velocityEngine, String templateLocation, String encoding,
            Map<String, Object> model, Writer writer) throws VelocityException {

        VelocityContext velocityContext = new VelocityContext(model);
        velocityEngine.mergeTemplate(templateLocation, encoding, velocityContext, writer);
    }

    /**
     * Merge the specified Velocity template with the given model into a String.
     * <p>When using this method to prepare a text for a mail to be sent with Spring's
     * mail support, consider wrapping VelocityException in MailPreparationException.
     *
     * @param velocityEngine   VelocityEngine to work with
     * @param templateLocation the location of template, relative to Velocity's resource loader path
     * @param encoding         the encoding of the template file
     * @param model            the Map that contains model names as keys and model objects as values
     * @return the result as String
     * @throws VelocityException if the template wasn't found or rendering failed
     */
    public static String mergeTemplateIntoString(VelocityEngine velocityEngine, String templateLocation,
                                                 String encoding, Map<String, Object> model) throws VelocityException {

        StringWriter result = new StringWriter();
        mergeTemplate(velocityEngine, templateLocation, encoding, model, result);
        return result.toString();
    }

    /**
     * Init velocity.
     *
     * @param velocityEngine the velocity engine
     * @param properties     the properties
     */
    public static void initVelocity(VelocityEngine velocityEngine, Properties properties) {
        initVelocityResourceLoader(velocityEngine, properties.getProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH));
    }

    /**
     * Init velocity resource loader.
     *
     * @param velocityEngine     the velocity engine
     * @param resourceLoaderPath the resource loader path
     */
    protected static void initVelocityResourceLoader(VelocityEngine velocityEngine, String resourceLoaderPath) {
        // Try to load via the file system, fall back to SpringResourceLoader
        // (for hot detection of template changes, if possible).
        try {
            StringBuilder resolvedPath = new StringBuilder();
            String[] paths = StringUtils.commaDelimitedListToStringArray(resourceLoaderPath);
            for (int i = 0; i < paths.length; i++) {
                String path = paths[i];
                Resource resource = resourceLoader.getResource(path);
                File file = resource.getFile();  // will fail if not resolvable in the file system
                if (logger.isDebugEnabled()) {
                    logger.debug("Resource loader path [" + path + "] resolved to file [" + file.getAbsolutePath() + "]");
                }
                resolvedPath.append(file.getAbsolutePath());
                if (i < paths.length - 1) {
                    resolvedPath.append(',');
                }
            }
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
            velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
            velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, resolvedPath.toString());
        } catch (IOException ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Cannot resolve resource loader path [" + resourceLoaderPath +
                        "] to [java.io.File]", ex);
            }
        }
    }
}
