package uk.co.itstherules.yawf.dispatcher;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Action {
    public abstract String value() default "Show";
    public abstract boolean requiresObjectCache() default true;
}