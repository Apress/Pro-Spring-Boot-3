package com.apress.myretro.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface MyRetroAudit {
    boolean showArgs() default false;
    MyRetroAuditOutputFormat format() default MyRetroAuditOutputFormat.TXT;

    MyRetroAuditIntercept intercept() default MyRetroAuditIntercept.BEFORE;

    String message() default "";

    boolean prettyPrint() default false;
}
