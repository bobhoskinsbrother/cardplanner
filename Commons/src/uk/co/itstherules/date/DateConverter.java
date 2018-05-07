package uk.co.itstherules.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	
	private static final SimpleDateFormat ISO_8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static SimpleDateFormat HUMAN_READABLE =  new SimpleDateFormat("MMMM d, yyyy");

	
	public String toDate(Date date) {
		try {
			return toHumanReadable(date);
        } catch (Exception e) {
    		try {
				return toISO8601(date);
            } catch (Exception e1) {
            	throw new RuntimeException(e1);
            }
        }
	}

	public Date fromDate(String date) {
		try {
			return fromHumanReadable(date);
        } catch (Exception e) {
    		try {
				return fromISO8601(date);
            } catch (Exception e1) {
            	throw new RuntimeException(e1);
            }
        }
	}
	
	public String toISO8601(Date date) {
	    return to(date, ISO_8601);
    }
	
	public Date fromISO8601(String date) {
		return from(date, ISO_8601);
    }
	
	public String toHumanReadable(Date date) {
	    return to(date, HUMAN_READABLE);
    }
	
	public Date fromHumanReadable(String date) {
		return from(date, HUMAN_READABLE);
    }
	
	public Date from(String date, DateFormat formatter) {
	    try {
	        return formatter.parse(date);
        } catch (ParseException e) {
	        throw new RuntimeException(e);
        }
    }
	
	private String to(Date date, DateFormat formatter) {
	    return formatter.format(date);
	}
	
}
