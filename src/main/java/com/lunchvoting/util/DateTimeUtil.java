package com.lunchvoting.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    private DateTimeUtil() {}

    public static final LocalDateTime CURRENT_DAY_START_DATE_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));

    public static final LocalDateTime CURRENT_DAY_END_DATE_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
}
