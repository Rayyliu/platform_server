package com.platform.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.registry.NacosAutoServiceRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyNacosLifecycle implements ApplicationContextAware {

    @Autowired(required = false)
    private NacosAutoServiceRegistration registration;

    @Autowired
    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (registration != null) {
            String port = environment.getProperty("server.port");
            registration.setPort(Integer.parseInt(port));
            registration.start();
        }
    }
}
