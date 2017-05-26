package robot.client.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Feng on 2017/5/26.
 */
public class DateTimeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    private static String DATETIME_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取long对应的日期时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String getStrByLong(Long date, String format) {
        if (date == null || format == null) {
            return null;
        }
        if (date <= 0) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(date);
    }

    /**
     * 获取long对应的日期时间
     *
     * @param date
     * @return
     */
    public static String getStrByLong(Long date) {
        return getStrByLong(date, DATETIME_FORMAT_TYPE);
    }


    public static final LocalDate getLocalDate (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
}
