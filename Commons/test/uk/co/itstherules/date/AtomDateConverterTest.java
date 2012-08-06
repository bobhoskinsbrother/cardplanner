package uk.co.itstherules.date;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class AtomDateConverterTest {
	
	@Test public void canConvert() {
		Date date = new CalendarDateFactory().createDateTime(2005, CalendarBuilder.Month.JULY, 31, 12, 29, 29);
		DateConverter unit = new DateConverter();
		Assert.assertEquals("2005-07-31T12:29:29Z", unit.toISO8601(date));
    }
	
}
