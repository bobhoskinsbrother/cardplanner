//$Id: SerializabilityValidator.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator.test;

import java.io.Serializable;

import org.hibernate.validator.Validator;

/**
 * Sample of a bean-level validator
 *
 * @author Emmanuel Bernard
 */
public class SerializabilityValidator implements Validator<Serializability>, Serializable {
	public boolean isValid(Object value) {
		return value instanceof Serializable;
	}

	public void initialize(Serializability parameters) {

	}
}
