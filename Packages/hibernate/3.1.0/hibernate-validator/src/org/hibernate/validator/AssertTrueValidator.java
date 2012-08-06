//$Id: AssertTrueValidator.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import java.io.Serializable;


/**
 * Check whether an element is true or not.
 *
 * @author Gavin King
 * @author Hardy Ferentschik
 */
public class AssertTrueValidator implements Validator<AssertTrue>, Serializable {

    public boolean isValid(Object value) {
        if (value == null) return true;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public void initialize(AssertTrue parameters) {
    }

}
