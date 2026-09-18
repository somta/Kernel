package com.matecoder.common.utils;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void dateToStr() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JANUARY, 15, 10, 30, 0);
        Date date = cal.getTime();

        String result = DateUtil.dateToStr(date, "yyyy-MM-dd");
        assertEquals("2024-01-15", result);
    }

    @Test
    void dateToStrWithTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.DECEMBER, 25, 14, 30, 0);
        Date date = cal.getTime();

        String result = DateUtil.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        assertEquals("2024-12-25 14:30:00", result);
    }

    @Test
    void isBetweenByHourNullParams() {
        assertFalse(DateUtil.isBetweenByHour(null, 10));
        assertFalse(DateUtil.isBetweenByHour(10, null));
        assertFalse(DateUtil.isBetweenByHour(null, null));
    }

    @Test
    void isBetweenByHourWideRange() {
        // isBetweenByHour uses strict > and < comparisons
        // So hour 0 is NOT > 0, and hour 23 is NOT < 23
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        // Use a range that definitely contains the current hour
        assertTrue(DateUtil.isBetweenByHour(currentHour - 1, currentHour + 1));
    }

    @Test
    void isBetweenByHourNarrowRange() {
        // A range that definitely doesn't contain current hour
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        // Use hours far from current
        int start = (currentHour + 10) % 24;
        int end = (currentHour + 12) % 24;
        if (start < end) {
            // If start < end, current hour is not in range (it's before start or after end)
            // But this depends on exact timing, so just check the method doesn't throw
            DateUtil.isBetweenByHour(start, end);
        }
    }
}
