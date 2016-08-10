package com.cloudcode.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Configurable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Configurable
public @interface ModuleConfig {
String name();
String basePackage() default "";
String [] domainPackages() default "";
}
