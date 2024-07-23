package com.apress.myretro.annotations;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EnableMyRetroAuditValueProvider implements BeanFactoryPostProcessor {

    private static MyRetroAuditStorage storage = MyRetroAuditStorage.DATABASE;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String beanName = Arrays.stream(beanFactory.getBeanNamesForAnnotation(EnableMyRetroAudit.class)).findFirst().orElse(null);
        if (beanName != null) {
            storage = beanFactory.findAnnotationOnBean(beanName, EnableMyRetroAudit.class).storage();
        }
    }

    public static MyRetroAuditStorage getStorage() {
        return storage;
    }
}
