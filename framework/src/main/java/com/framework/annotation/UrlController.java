package com.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// avec annotation 
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UrlController{
    String value() default "";
}
