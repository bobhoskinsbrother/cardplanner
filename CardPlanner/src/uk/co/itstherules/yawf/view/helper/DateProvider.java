package uk.co.itstherules.yawf.view.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public final class DateProvider {
	
	public String now() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy_hh.mm");
		return formatter.format(nowCal().getTime());
	}
	
	protected Calendar nowCal() {
		return GregorianCalendar.getInstance();
	}
	
}
