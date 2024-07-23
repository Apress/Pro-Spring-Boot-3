package com.apress.myretro.configuration;

import com.apress.myretro.annotations.EnableMyRetroAuditValueProvider;
import com.apress.myretro.aop.MyRetroAuditAspect;
import com.apress.myretro.listener.MyRetroAuditListener;
import com.apress.myretro.model.MyRetroAuditEventRepository;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties(MyRetroAuditProperties.class)
@Conditional(MyRetroAuditCondition.class)
@EnableJpaRepositories(basePackages = "com.apress")
@ComponentScan(basePackages = "com.apress")
@EntityScan(basePackages = "com.apress")
@AutoConfiguration
public class MyRetroAuditConfiguration {

    @Bean
    public MyRetroAuditListener myRetroListener() {
        return new MyRetroAuditListener();
    }

    @Bean
    public MyRetroAuditAspect myRetroAuditAspect(MyRetroAuditEventRepository myRetroAuditEventRepository, MyRetroAuditProperties properties) {
        return new MyRetroAuditAspect(myRetroAuditEventRepository, properties, EnableMyRetroAuditValueProvider.getStorage());
    }

}
