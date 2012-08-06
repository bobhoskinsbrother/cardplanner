package uk.co.itstherules.date;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class CalendarMathTest {
	
	@Test
    public void canSimpleDayDiff() throws Exception {
	    Calendar start = setupCalendar(Calendar.getInstance());
	    Calendar end = setupCalendar(Calendar.getInstance());
	    
	    assertEquals(0L, CalendarDateMath.dayDiff(start, end));
	    end.add(Calendar.DAY_OF_MONTH, 1);
	    assertEquals(1L, CalendarDateMath.dayDiff(start, end));
	    end.add(Calendar.DAY_OF_MONTH, 112);
	    assertEquals(114L, CalendarDateMath.dayDiff(start, end));
	    end.add(Calendar.DAY_OF_MONTH, 252);
	    assertEquals(366L, CalendarDateMath.dayDiff(start, end));
	    end.add(Calendar.DAY_OF_MONTH, 1);
	    assertEquals(367L, CalendarDateMath.dayDiff(start, end));
    }
	
	@Test public void canReverseDayDiff() throws Exception {
	    Calendar start = setupCalendar(Calendar.getInstance());
	    Calendar end = setupCalendar(Calendar.getInstance());
	    end.add(Calendar.DAY_OF_MONTH, -112);
	    
	    assertEquals(112L, CalendarDateMath.dayDiff(start, end));
    }
	
	
	private Calendar setupCalendar(Calendar calendar) {
		calendar.set(Calendar.YEAR, 1991);
		calendar.set(Calendar.MONTH, Calendar.MARCH);
		calendar.set(Calendar.DAY_OF_MONTH, 10);
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 1);
		calendar.set(Calendar.SECOND, 1);
		calendar.set(Calendar.MILLISECOND, 1);
		return calendar;
	}
}
