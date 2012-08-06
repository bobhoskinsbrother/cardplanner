//$Id: MinValidator.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;

/**
 * Do check a min restriction on a numeric (whether and actual number or its string representation,
 * and apply expected contraints on hibernate metadata.
 *
 * @author Gavin King
 */
public class MinValidator implements Validator<Min>, PropertyConstraint, Serializable {

	private long min;

	public void initialize(Min parameters) {
		min = parameters.value();
	}

	public boolean isValid(Object value) {
		if ( value == null ) return true;
		if ( value instanceof String ) {
			try {
				return new BigDecimal( (String) value ).compareTo( BigDecimal.valueOf(min) ) >= 0;
			}
			catch (NumberFormatException nfe) {
				return false;
			}
		}
		else if ( ( value instanceof Double ) || ( value instanceof Float ) ) {
			double dv = ( (Number) value ).doubleValue();
			return dv >= min;
		}
		else if ( value instanceof BigInteger ) {
			return ( (BigInteger) value ).compareTo( BigInteger.valueOf( min ) ) >= 0;
		}
		else if ( value instanceof BigDecimal ) {
			return ( (BigDecimal) value ).compareTo( BigDecimal.valueOf( min ) ) >= 0;
		}
		else if ( value instanceof Number ) {
			long lv = ( (Number) value ).longValue();
			return lv >= min;
		}
		else {
			return false;
		}
	}

	public void apply(Property property) {
		Column col = (Column) property.getColumnIterator().next();
		col.setCheckConstraint( col.getName() + ">=" + min );
	}
}
