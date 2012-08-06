//$Id: Patterns.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

/**
 * The annotated element must follow the list of regexp patterns
 *
 * @author Emmanuel Bernard
 */
@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Patterns {
	Pattern[] value();
}
