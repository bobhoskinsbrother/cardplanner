package uk.co.itstherules.date;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class CalendarDateFactoryTest {
	
	@Test public void canMakeDate() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDate(2002, CalendarBuilder.Month.JUNE, 4);
		
		Calendar queryable = Calendar.getInstance();
		queryable.setTime(reply);
		
		Assert.assertEquals(queryable.get(Calendar.YEAR), 2002);
		Assert.assertEquals(queryable.get(Calendar.MONTH), Calendar.JUNE);
		Assert.assertEquals(queryable.get(Calendar.DAY_OF_MONTH), 4);
		Assert.assertEquals(queryable.get(Calendar.HOUR_OF_DAY), 0);
		Assert.assertEquals(queryable.get(Calendar.MINUTE), 0);
		Assert.assertEquals(queryable.get(Calendar.SECOND), 0);
		Assert.assertEquals(queryable.get(Calendar.MILLISECOND), 0);
		
	}


	@Test public void canMakeTimeToMinutes() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(2008, CalendarBuilder.Month.JANUARY, 14, 15, 33);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		Assert.assertEquals(queriable.get(Calendar.YEAR), 2008);
		Assert.assertEquals(queriable.get(Calendar.MONDAY), Calendar.JANUARY);
		Assert.assertEquals(queriable.get(Calendar.DATE), 14);
		Assert.assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 15);
		Assert.assertEquals(queriable.get(Calendar.MINUTE), 33);
		Assert.assertEquals(queriable.get(Calendar.SECOND), 0);
		Assert.assertEquals(queriable.get(Calendar.MILLISECOND), 0);
		
	}

	@Test public void canMakeTimeToSecs() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(1999, CalendarBuilder.Month.DECEMBER, 1, 8, 20, 12);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		Assert.assertEquals(queriable.get(Calendar.YEAR), 1999);
		Assert.assertEquals(queriable.get(Calendar.MONDAY), Calendar.DECEMBER);
		Assert.assertEquals(queriable.get(Calendar.DATE), 1);
		Assert.assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 8);
		Assert.assertEquals(queriable.get(Calendar.MINUTE), 20);
		Assert.assertEquals(queriable.get(Calendar.SECOND), 12);
		Assert.assertEquals(queriable.get(Calendar.MILLISECOND), 0);
		
	}

	@Test public void canMakeTimeToMillis() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(2011, CalendarBuilder.Month.APRIL, 1, 10, 0, 21, 600);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		Assert.assertEquals(queriable.get(Calendar.YEAR), 2011);
		Assert.assertEquals(queriable.get(Calendar.MONDAY), Calendar.APRIL);
		Assert.assertEquals(queriable.get(Calendar.DATE), 1);
		Assert.assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 10);
		Assert.assertEquals(queriable.get(Calendar.MINUTE), 0);
		Assert.assertEquals(queriable.get(Calendar.SECOND), 21);
		Assert.assertEquals(queriable.get(Calendar.MILLISECOND), 600);
		
	}

}
