package uk.co.itstherules.yawf.inbound.annotations.oval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.configuration.annotation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(checkWith=IsAlphanumericCheck.class)
public @interface IsAlphanumeric {
	String message() default "Must be alphanumeric";
}
