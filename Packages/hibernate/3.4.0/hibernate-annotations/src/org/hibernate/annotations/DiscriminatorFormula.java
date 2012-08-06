//$Id: DiscriminatorFormula.java 14736 2008-06-04 14:23:42Z hardy.ferentschik $
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Discriminator formula
 * To be placed at the root entity.
 *
 * @author Emmanuel Bernard
 * @see Formula
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface DiscriminatorFormula {
	String value();
}
