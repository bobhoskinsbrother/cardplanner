package uk.co.itstherules.date;

import java.util.Calendar;
import java.util.Date;

public class CalendarDateMath {

    public static long dayDiff(Calendar dateOne, Calendar dateTwo) {
        Calendar startDate;
        Calendar endDate;
        if(dateOne.before(dateTwo)) {
            startDate = Calendar.class.cast(dateOne.clone());
            endDate = dateTwo;
        } else {
            startDate = Calendar.class.cast(dateTwo.clone());
            endDate = dateOne;
        }
        long daysBetween = 0;
        while (startDate.before(endDate)) {
            startDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }


    public static long dayDiff(Date start, Date end) {
        CalendarDateFactory factory = new CalendarDateFactory();
        return CalendarDateMath.dayDiff(factory.createCalendar(start), factory
                .createCalendar(end));
    }
}
