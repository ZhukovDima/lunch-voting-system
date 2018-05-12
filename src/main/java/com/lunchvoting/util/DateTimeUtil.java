package com.lunchvoting.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    private DateTimeUtil() {}

    public static final LocalDateTime CURRENT_DAY_START_DATE_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

    public static final LocalDateTime CURRENT_DAY_END_DATE_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));

    public static LocalDate parseLocalDate(String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }
}
