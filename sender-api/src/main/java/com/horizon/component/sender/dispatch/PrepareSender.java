package com.horizon.component.sender.dispatch;


import com.horizon.component.sender.MimeMessage;
import com.horizon.component.sender.Validator;
import com.horizon.component.sender.basic.DefaultValidator;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Created by zhenzhong on 2015/9/24.
 */
public class PrepareSender {

    private static final Logger LOG = LoggerFactory
            .getLogger(PrepareSender.class);
    private static final ServiceLoader<Validator> sl = ServiceLoader.load(Validator.class);

    private MimeMessage mimeMessage;

    public PrepareSender(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    private static final VelocityEngine velocityEngine = new VelocityEngine();

    static {
        Properties properties = new Properties();
        try {
            LOG.info("Initialize the config info.");
            properties.load(PrepareSender.class.getResourceAsStream("velocity.properties"));
        } catch (IOException e) {
            LOG.error("Load config file error: {}", e.toString());
        }
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, properties.getProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH));
    }

    /**
     * Get content from template if the content is null
     *
     * @throws Exception
     */
    public void ParseTemplateContent() throws Exception {
        String content = mimeMessage.getContent();
        if (content != null && content.length() > 0) {
            LOG.info("get content from client request.");
        } else {
            StringBuffer buffer = new StringBuffer(mimeMessage.getSendType().toLowerCase()).append("_");
            String[] tempId = mimeMessage.getTemplate().getTemplateId().split("\\.");
            String lang = "_" + (mimeMessage.getTemplate().getLanguage() == null ? "en" : mimeMessage.getTemplate().getLanguage());

            buffer.append(tempId[0]);
            if (!tempId[0].endsWith(lang)) {
                buffer.append(lang);
            }
            buffer.append(".vm");

            LOG.info("get content from template [{}].", buffer);
            content = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, buffer.toString(), "UTF-8", mimeMessage.getTemplate().getModel());
            if (content == null || content.length() == 0) {
                LOG.info("Can not find the template [{}]", buffer);
                throw new NullPointerException("Not found the template \'" + buffer.toString() + "\'");
            }
        }
        mimeMessage.setContent(content);
    }

    public final PrepareSender validate() throws Exception {
        if (mimeMessage == null)
            throw new NullPointerException("Mime message is null.");
        final String sendType = mimeMessage.getSendType();
        for (Validator validator : sl) {
            if (validator.isSupported(sendType)) {
                validator.validate(mimeMessage);
                return this;
            }
        }
        LOG.info("Not supported sender \'" + sendType + "\'.");
        LOG.info("Use default validator.");
        Validator validator = new DefaultValidator();
        validator.validate(mimeMessage);
        return this;
    }

}
