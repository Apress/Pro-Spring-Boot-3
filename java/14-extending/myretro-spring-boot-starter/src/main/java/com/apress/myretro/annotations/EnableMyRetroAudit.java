package com.apress.myretro.annotations;

import com.apress.myretro.configuration.MyRetroAuditConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MyRetroAuditConfiguration.class)
public @interface EnableMyRetroAudit {
    MyRetroAuditStorage storage() default MyRetroAuditStorage.DATABASE;
}
