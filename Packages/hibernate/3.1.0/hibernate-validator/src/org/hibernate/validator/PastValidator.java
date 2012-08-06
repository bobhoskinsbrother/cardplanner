//$Id: PastValidator.java 15133 2008-08-20 10:05:57Z hardy.ferentschik $
package org.hibernate.validator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Property;

/**
 * Check that a given date is in the past, and apply the same restriction
 * at the DB level
 *
 * @author Gavin King
 */
public class PastValidator implements Validator<Past>, PropertyConstraint, Serializable {

	public void initialize(Past parameters) {
	}

	public boolean isValid(Object value) {
		if ( value == null ) return true;
		if ( value instanceof Date ) {
			Date date = (Date) value;
			return date.before( new Date() );
		}
		else if ( value instanceof Calendar ) {
			Calendar cal = (Calendar) value;
			return cal.before( Calendar.getInstance() );
		}
		else {
			return false;
		}
	}

//  ANSI SQL does not allow the use of current_date on check constraints :(
//	public void apply(Property property) {
//		Column col = (Column) property.getColumnIterator().next();
//		col.setCheckConstraint( col.getName() + " < current_date" );
//	}

	//TODO Keep it here for ABI compatibility. Should be removed whe moving to Bean Validation
	public void apply(Property property) {
	}
}
