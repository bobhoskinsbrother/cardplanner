package uk.co.itstherules.yawf.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

public final class HeaderCachingInformation {

    private HeaderCachingInformation() {  }

    public static void setAsNeverAskAgain(HttpServletResponse response) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.roll(Calendar.YEAR, 10);
        response.setHeader("Cache-Control", new StringBuffer("STATIC, max-age=").append("315569259747").toString());
        response.setHeader("Expires", expiresDateFormatter().format(calendar.getTime()));
    }

    public static void setAsAlwaysAsk(HttpServletResponse response) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.roll(Calendar.SECOND, -10);
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Expires", expiresDateFormatter().format(calendar.getTime()));
    }

    private static DateFormat expiresDateFormatter() {
        DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return httpDateFormat;
    }


}
