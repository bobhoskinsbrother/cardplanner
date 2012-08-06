package uk.co.itstherules.date;


import java.util.Calendar;
import java.util.Date;

public final class DateTimeBuilder {

    private CalendarBuilder delegate;

    public DateTimeBuilder() {
        this.delegate = new CalendarBuilder();
    }

    public DateTimeBuilder(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        this.delegate = new CalendarBuilder(instance);
    }

    public DateTimeBuilder(long milliSeconds) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(milliSeconds);
        this.delegate = new CalendarBuilder(instance);
    }

    public DateTimeBuilder y(int year) {
        this.delegate.y(year);
        return this;
    }

    public DateTimeBuilder m(CalendarBuilder.Month month) {
        this.delegate.m(month);
        return this;
    }

    public DateTimeBuilder d(int day) {
        this.delegate.d(day);
        return this;
    }

    public DateTimeBuilder m(int minute) {
        this.delegate.m(minute);
        return this;
    }

    public DateTimeBuilder s(int second) {
        this.delegate.s(second);
        return this;
    }

    public DateTimeBuilder milli(int milli) {
        this.delegate.milli(milli);
        return this;
    }
    
    public Date build() {
        return new Date(this.delegate.build().getTimeInMillis());
    }

    public DateTimeBuilder h(int hour) {
        this.delegate.h(hour);
        return this;
    }
}
