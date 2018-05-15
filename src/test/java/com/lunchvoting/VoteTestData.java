package com.lunchvoting;
import com.lunchvoting.model.Vote;
import com.lunchvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {

    public static final int MENU1_VOTE1_ID = 0;

    public static final Vote R1_USER1_VOTE1 = new Vote(null, R_1_MENU_1, USER1);

    public static final Vote USER1_VOTE1_UPDATED_R1_TO_R2 =
            new Vote(MENU1_VOTE1_ID, R_2_MENU_1, USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59,0)));
    public static final Vote R1_USER1_VOTE1_TIME_VIOLATION =
            new Vote(MENU1_VOTE1_ID, R_2_MENU_1, USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0,0)));

    public static Vote getNew() {
        return new Vote(null, R_1_MENU_1, USER1);
    }

    public static VoteTo getNewVoteTo() {
        return new VoteTo(R_1_MENU_1_ID);
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorOnFields("id").isEqualTo(expected);
    }
}
