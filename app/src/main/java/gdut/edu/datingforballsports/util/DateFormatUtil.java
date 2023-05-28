package gdut.edu.datingforballsports.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatUtil {
    private static SimpleDateFormat sif;

    public static String getCurrentDateString(String dateFormat) {
        sif = new SimpleDateFormat(dateFormat);
        return sif.format(new Date());
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date getDate(String dateFormat, String date) throws ParseException {
        sif = new SimpleDateFormat(dateFormat);
        return sif.parse(date);
    }
}
