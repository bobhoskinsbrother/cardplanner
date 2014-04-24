package uk.co.itstherules.yawf.inbound.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface QueryKey {
    public abstract String value();
    public abstract CacheInstruction cache() default CacheInstruction.NotFromCache;
    public abstract boolean follow() default true;
}