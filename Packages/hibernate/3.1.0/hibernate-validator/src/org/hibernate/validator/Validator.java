//$Id: Validator.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import java.lang.annotation.Annotation;

/**
 * A constraint validator for a particular annotation
 *
 * @author Gavin King
 */
public interface Validator<A extends Annotation> {
	/**
	 * does the object/element pass the constraints
	 */
	public boolean isValid(Object value);

	/**
	 * Take the annotations values
	 *
	 * @param parameters
	 */
	public void initialize(A parameters);
}
