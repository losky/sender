package com.horizon.component.utilities;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * interface defined by
 *
 * @author ZhenZhong
 * @date 2016 /7/8
 */
@Component
@Order
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * Instantiates a new Spring context util.
     */
    public SpringContextUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Gets bean.
     *
     * @param name the name
     * @return the bean
     * @throws BeansException the beans exception
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * Gets bean.
     *
     * @param <T>          the type parameter
     * @param name         the name
     * @param requiredType the required type
     * @return the bean
     * @throws BeansException the beans exception
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * Contains bean boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * Is singleton boolean.
     *
     * @param name the name
     * @return the boolean
     * @throws NoSuchBeanDefinitionException the no such bean definition exception
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    /**
     * Gets type.
     *
     * @param name the name
     * @return the type
     * @throws NoSuchBeanDefinitionException the no such bean definition exception
     */
    public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    /**
     * Get aliases string [ ].
     *
     * @param name the name
     * @return the string [ ]
     * @throws NoSuchBeanDefinitionException the no such bean definition exception
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }
}