package uk.co.itstherules.yawf.inbound.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.co.itstherules.yawf.inbound.binders.QueryValueBinder;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UseBinder {
    public abstract Class<? extends QueryValueBinder> value();
}