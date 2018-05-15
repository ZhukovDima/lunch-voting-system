package com.lunchvoting;
import com.lunchvoting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {

    public static final int MENU1_VOTE1_ID = 1;

    public static final Vote R1_USER1_VOTE1 = new Vote(MENU1_VOTE1_ID, R_1_MENU_1, USER1);

    public static final Vote USER1_VOTE1_UPDATED_R1_TO_R2 =
            new Vote(MENU1_VOTE1_ID, R_2_MENU_1, USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59,0)));

    public static final Vote R1_USER1_VOTE1_TIME_VIOLATION =
            new Vote(MENU1_VOTE1_ID, R_2_MENU_1, USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0,0)));

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id");
    }
}
