package uk.co.itstherules.cardplanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import uk.co.itstherules.cardplanner.DateCalendarFactory;

public class DateCalendarFactoryTest {

	@Test
	public void canCreate(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.YEAR, 2002);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 20);
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 20);
		calendar.set(Calendar.SECOND, 20);
		calendar.set(Calendar.MILLISECOND, 100);
		Calendar reply = DateCalendarFactory.instance(calendar.getTime());
		Assert.assertEquals(2002, reply.get(Calendar.YEAR));
		Assert.assertEquals(Calendar.JANUARY, reply.get(Calendar.MONTH));
		Assert.assertEquals(20, reply.get(Calendar.DATE));
		Assert.assertEquals(0, reply.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(0, reply.get(Calendar.MINUTE));
		Assert.assertEquals(0, reply.get(Calendar.SECOND));
		Assert.assertEquals(0, reply.get(Calendar.MILLISECOND));
	}
	
	@Test
	public void canCreateDiffDate(){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.YEAR, 2012);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 12);
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 12);
		calendar.set(Calendar.SECOND, 12);
		calendar.set(Calendar.MILLISECOND, 12);
		Calendar reply = DateCalendarFactory.instance(calendar.getTime());
		Assert.assertEquals(2012, reply.get(Calendar.YEAR));
		Assert.assertEquals(Calendar.JANUARY, reply.get(Calendar.MONTH));
		Assert.assertEquals(12, reply.get(Calendar.DATE));
		Assert.assertEquals(0, reply.get(Calendar.HOUR));
		Assert.assertEquals(0, reply.get(Calendar.MINUTE));
		Assert.assertEquals(0, reply.get(Calendar.SECOND));
		Assert.assertEquals(0, reply.get(Calendar.MILLISECOND));
	}
}
