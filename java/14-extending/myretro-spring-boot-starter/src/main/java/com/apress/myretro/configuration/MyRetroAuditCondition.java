package com.apress.myretro.configuration;

import com.apress.myretro.annotations.EnableMyRetroAudit;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MyRetroAuditCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getBeanFactory().getBeansWithAnnotation(EnableMyRetroAudit.class).size() > 0;
    }
}
