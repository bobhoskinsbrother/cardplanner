package uk.co.itstherules.yawf.inbound.annotations.oval;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.configuration.annotation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(checkWith=IsPackageNameCheck.class)
public @interface IsPackageName {
	String message() default "Must be a valid package name";
}
