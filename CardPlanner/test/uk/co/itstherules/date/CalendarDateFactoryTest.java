package uk.co.itstherules.date;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CalendarDateFactoryTest {
	
	@Test public void canMakeDate() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDate(2002, CalendarBuilder.Month.JUNE, 4);
		
		Calendar queryable = Calendar.getInstance();
		queryable.setTime(reply);
		
		assertEquals(queryable.get(Calendar.YEAR), 2002);
		assertEquals(queryable.get(Calendar.MONTH), Calendar.JUNE);
		assertEquals(queryable.get(Calendar.DAY_OF_MONTH), 4);
		assertEquals(queryable.get(Calendar.HOUR_OF_DAY), 0);
		assertEquals(queryable.get(Calendar.MINUTE), 0);
		assertEquals(queryable.get(Calendar.SECOND), 0);
		assertEquals(queryable.get(Calendar.MILLISECOND), 0);
		
	}


	@Test public void canMakeTimeToMinutes() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(2008, CalendarBuilder.Month.JANUARY, 14, 15, 33);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		assertEquals(queriable.get(Calendar.YEAR), 2008);
		assertEquals(queriable.get(Calendar.MONDAY), Calendar.JANUARY);
		assertEquals(queriable.get(Calendar.DATE), 14);
		assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 15);
		assertEquals(queriable.get(Calendar.MINUTE), 33);
		assertEquals(queriable.get(Calendar.SECOND), 0);
		assertEquals(queriable.get(Calendar.MILLISECOND), 0);
		
	}

	@Test public void canMakeTimeToSecs() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(1999, CalendarBuilder.Month.DECEMBER, 1, 8, 20, 12);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		assertEquals(queriable.get(Calendar.YEAR), 1999);
		assertEquals(queriable.get(Calendar.MONDAY), Calendar.DECEMBER);
		assertEquals(queriable.get(Calendar.DATE), 1);
		assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 8);
		assertEquals(queriable.get(Calendar.MINUTE), 20);
		assertEquals(queriable.get(Calendar.SECOND), 12);
		assertEquals(queriable.get(Calendar.MILLISECOND), 0);
		
	}

	@Test public void canMakeTimeToMillis() {
		CalendarDateFactory unit = new CalendarDateFactory();
		Date reply = unit.createDateTime(2011, CalendarBuilder.Month.APRIL, 1, 10, 0, 21, 600);
		
		Calendar queriable = Calendar.getInstance();
		queriable.setTime(reply);
		
		assertEquals(queriable.get(Calendar.YEAR), 2011);
		assertEquals(queriable.get(Calendar.MONDAY), Calendar.APRIL);
		assertEquals(queriable.get(Calendar.DATE), 1);
		assertEquals(queriable.get(Calendar.HOUR_OF_DAY), 10);
		assertEquals(queriable.get(Calendar.MINUTE), 0);
		assertEquals(queriable.get(Calendar.SECOND), 21);
		assertEquals(queriable.get(Calendar.MILLISECOND), 600);
		
	}

}
