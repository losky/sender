/*
 * Copyright (c) 2015.
 *
 */

package com.horizon.component.sender.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * spring bean configuration without xml
 * Created by
 *
 * @author ZhenZhong
 * @date 2015/10/27
 */
@Configuration
@PropertySource({"classpath:mail.properties"})
public class ComponentConfiguration {

    /**
     * com.horizon.component.sender.email component smtp configuration
     *
     * @param env
     *
     * @return
     */
    @Bean(name = "mailSenderConfig")
    public JavaMailSenderImpl mailSenderConfig(Environment env) {
        JavaMailSenderImpl config = new JavaMailSenderImpl();
        config.setHost(env.getRequiredProperty("mail.host"));
        config.setPort(env.getRequiredProperty("mail.port", Integer.class));
        config.setUsername(env.getRequiredProperty("mail.username"));
        config.setPassword(env.getRequiredProperty("mail.password"));

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", env.getRequiredProperty("mail.smtp.auth"));
        javaMailProperties.setProperty("mail.smtp.timeout", env.getRequiredProperty("mail.smtp.timeout"));
        javaMailProperties.setProperty("mail.smtp.starttls.enable", env.getRequiredProperty("mail.smtp.starttls.enable"));
        javaMailProperties.setProperty("mail.smtp.socketFactory.class", env.getRequiredProperty("mail.smtp.socketFactory.class"));
        javaMailProperties.setProperty("mail.smtp.debug", env.getRequiredProperty("mail.smtp.debug"));
        javaMailProperties.setProperty("mail.smtp.socketFactory.fallback", env.getRequiredProperty("mail.smtp.socketFactory.fallback"));

        config.setJavaMailProperties(javaMailProperties);
        return config;
    }

    /**
     * velocity template set
     *
     * @return
     *
     * @throws IOException
     */
    @Bean(name = "velocityEngine")
    public VelocityEngine velocityEngine() throws IOException {
        VelocityEngineFactory velocityEngineFactory = new VelocityEngineFactoryBean();
        velocityEngineFactory.setResourceLoaderPath("/WEB-INF/template/");
        return velocityEngineFactory.createVelocityEngine();
    }

    @Bean(name = "taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return taskExecutor;
    }

}
