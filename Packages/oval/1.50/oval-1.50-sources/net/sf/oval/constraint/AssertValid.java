/*******************************************************************************
 * Portions created by Sebastian Thomschke are copyright (c) 2005-2010 Sebastian
 * Thomschke.
 * 
 * All Rights Reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Sebastian Thomschke - initial implementation.
 *******************************************************************************/
package net.sf.oval.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.oval.ConstraintTarget;
import net.sf.oval.ConstraintViolation;
import net.sf.oval.configuration.annotation.Constraint;

/**
 * Check if the value passes a validation by Validator.validate().
 * 
 * @author Sebastian Thomschke
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Constraint(checkWith = AssertValidCheck.class)
public @interface AssertValid
{
	/**
	 * <p>In case the constraint is declared for an array, collection or map this controls how the constraint is applied to it and it's child objects.
	 * 
	 * <p><b>Default:</b> ConstraintTarget.CONTAINER, ConstraintTarget.VALUES
	 * 
	 * <p><b>Note:</b> This setting is ignored for object types other than array, map and collection.
	 */
	ConstraintTarget[] appliesTo() default {ConstraintTarget.CONTAINER, ConstraintTarget.VALUES};

	/**
	 * error code passed to the ConstraintViolation object
	 */
	String errorCode() default "net.sf.oval.constraint.AssertValid";

	/**
	 * message to be used for the ContraintsViolatedException
	 * 
	 * @see ConstraintViolation
	 */
	String message() default "net.sf.oval.constraint.AssertValid.violated";

	/**
	 * The associated constraint profiles.
	 */
	String[] profiles() default {};

	/**
	 * Specifies if the keys and values of a collection/map must be valid too.
	 * @deprecated use applyTo instead
	 */
	@Deprecated
	boolean requireValidElements() default true;

	/**
	 * severity passed to the ConstraintViolation object
	 */
	int severity() default 0;

	/**
	 * Formula returning <code>true</code> if this constraint shall be evaluated and
	 * <code>false</code> if it shall be ignored for the current validation.
	 * <p>
	 * <b>Important:</b> The formula must be prefixed with the name of the scripting language that is used.
	 * E.g. <code>groovy:_this.amount > 10</code>
	 * <p>
	 * Available context variables are:<br>
	 * <b>_this</b> -&gt; the validated bean<br>
	 * <b>_value</b> -&gt; the value to validate (e.g. the field value, parameter value, method return value,
	 *    or the validated bean for object level constraints)
	 */
	String when() default "";

}
