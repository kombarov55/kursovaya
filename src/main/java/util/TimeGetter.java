package util;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * Created by nikolaykombarov on 23.03.17.
 */
public class TimeGetter {

    Calendar calendar;

    public TimeGetter() {
        calendar = Calendar.getInstance();
        calendar.set(HOUR, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
    }

    public Date addAndGetTime(int timeCode, int amount) {
        calendar.add(timeCode, amount);
        Date ret = calendar.getTime();
        calendar.add(timeCode, -amount);
        return ret;
    }

    public Date getTodaysMidnight() {
        return calendar.getTime();
    }

}
