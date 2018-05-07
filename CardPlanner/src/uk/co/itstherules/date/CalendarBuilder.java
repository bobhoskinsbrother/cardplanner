package uk.co.itstherules.date;

import java.util.Calendar;

public final class CalendarBuilder {

    private final Calendar delegate;

    public enum Month {

		JANUARY(Calendar.JANUARY),
		FEBRUARY(Calendar.FEBRUARY),
		MARCH(Calendar.MARCH),
		APRIL(Calendar.APRIL),
		MAY(Calendar.MAY),
		JUNE(Calendar.JUNE),
		JULY(Calendar.JULY),
		AUGUST(Calendar.AUGUST),
		SEPTEMBER(Calendar.SEPTEMBER),
		OCTOBER(Calendar.OCTOBER),
		NOVEMBER(Calendar.NOVEMBER),
		DECEMBER(Calendar.DECEMBER);

		private final int calendarRef;

		private Month(int calendarRef) { this.calendarRef = calendarRef; }

        public int getCalendarRef() { return calendarRef; }

    };

    public CalendarBuilder() {
        this.delegate = Calendar.getInstance();
        this.delegate.set(Calendar.YEAR, 0);
        this.delegate.set(Calendar.MONTH, Calendar.JANUARY);
        this.delegate.set(Calendar.DAY_OF_MONTH, 1);
        this.delegate.set(Calendar.HOUR_OF_DAY, 0);
        this.delegate.set(Calendar.MINUTE, 0);
        this.delegate.set(Calendar.SECOND, 0);
        this.delegate.set(Calendar.MILLISECOND, 0);
    }

    public CalendarBuilder(Calendar calendar) {
        this.delegate = Calendar.class.cast(calendar.clone());
    }


    public CalendarBuilder y(int year) {
        this.delegate.set(Calendar.YEAR, year);
        return this;
    }

    public CalendarBuilder m(Month month) {
        this.delegate.set(Calendar.MONTH, month.getCalendarRef());
        return this;
    }

    public CalendarBuilder d(int day) {
        this.delegate.set(Calendar.DAY_OF_MONTH, day);
        return this;
    }

    public CalendarBuilder h(int hour) {
        this.delegate.set(Calendar.HOUR_OF_DAY, hour);
        return this;
    }

    public CalendarBuilder m(int minute) {
        this.delegate.set(Calendar.MINUTE, minute);
        return this;
    }

    public CalendarBuilder s(int second) {
        this.delegate.set(Calendar.SECOND, second);
        return this;
    }

    public CalendarBuilder milli(int milli) {
        this.delegate.set(Calendar.MILLISECOND, milli);
        return this;
    }

    public Calendar build() {
        return Calendar.class.cast(delegate.clone());
    }
}
