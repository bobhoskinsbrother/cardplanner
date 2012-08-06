package uk.co.itstherules.yawf.view.helper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import uk.co.itstherules.date.DateConverter;

public final class DateHelper {
	
	public String now() { return new DateConverter().toISO8601(GregorianCalendar.getInstance().getTime()); }
	
	public String now(int increment) { 
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, increment);
		return new DateConverter().toISO8601(calendar.getTime()); 
	}
}
