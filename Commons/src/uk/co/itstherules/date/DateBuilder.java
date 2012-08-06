package uk.co.itstherules.date;


import java.util.Date;

public final class DateBuilder {

    private DateTimeBuilder delegate;

    public DateBuilder() {
        this.delegate = new DateTimeBuilder().h(0).m(0).s(0).milli(0);
    }

    public DateBuilder(Date date) {
        this.delegate = new DateTimeBuilder(date).h(0).m(0).s(0).milli(0);
    }

    public DateBuilder(long milliSeconds) {
        this.delegate = new DateTimeBuilder(milliSeconds).h(0).m(0).s(0).milli(0);
    }

    public DateBuilder y(int year) {
        this.delegate.y(year);
        return this;
    }

    public DateBuilder m(CalendarBuilder.Month month) {
        this.delegate.m(month);
        return this;
    }

    public DateBuilder d(int day) {
        this.delegate.d(day);
        return this;
    }

    public Date build() {
        return delegate.build();
    }
}
