package uk.co.itstherules.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarDateFactory {
	
	public Date createDate(int year, CalendarBuilder.Month month, int dayOfMonth) {
		return createDateTime(year, month, dayOfMonth, 0, 0);
	}

	public Date createDateTime(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute) {
		return createDateTime(year, month, dayOfMonth, hour, minute, 0);
	}

	public Date createDateTime(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute, int second) {
		return createDateTime(year, month, dayOfMonth, hour, minute, second, 0);
	}
	
	public Date createDateTime(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute, int second, int millisecond) {
	    return createCalendar(year, month, dayOfMonth, hour, minute, second, millisecond).getTime();
    }
	
	public Calendar createCalendar(Date date) {
		Calendar instance = GregorianCalendar.getInstance();
		instance.setTime(date);
		return instance;
	}

	public Calendar createCalendar(int year, CalendarBuilder.Month month, int dayOfMonth) {
		return createCalendar(year, month, dayOfMonth, 0, 0);
	}

	public Calendar createCalendar(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute) {
		return createCalendar(year, month, dayOfMonth, hour, minute, 0);
	}

	public Calendar createCalendar(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute, int second) {
		return createCalendar(year, month, dayOfMonth, hour, minute, second, 0);
	}
	
	public Calendar createCalendar(int year, CalendarBuilder.Month month, int dayOfMonth, int hour, int minute, int second, int millisecond) {
        return new CalendarBuilder().y(year).m(month).d(dayOfMonth).h(hour).m(minute).s(second).milli(millisecond).build();
    }
	
}
