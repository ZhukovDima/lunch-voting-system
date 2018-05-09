package com.lunchvoting;
import com.lunchvoting.model.Vote;
import com.lunchvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static com.lunchvoting.RestaurantTestData.RESTAURANT1;
import static com.lunchvoting.RestaurantTestData.RESTAURANT2;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {

    public static final int R1_VOTE1_ID = 1;
    public static final int R1_VOTE2_ID = 2;

    public static final Vote R1_USER1_VOTE1 = new Vote(R1_VOTE1_ID, RESTAURANT1, UserTestData.USER1);

    public static final Vote USER1_VOTE1_UPDATED_R1_TO_R2 =
            new Vote(R1_VOTE1_ID, RESTAURANT2, UserTestData.USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 59,0)));
    public static final Vote R1_USER1_VOTE1_TIME_VIOLATION =
            new Vote(R1_VOTE1_ID, RESTAURANT2, UserTestData.USER1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0,0)));

    public static VoteTo getNew() {
        return new VoteTo(null, RESTAURANT1);
    }

    public static Vote getNewByUser2() {
        return new Vote(null, RESTAURANT1, UserTestData.USER2);
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorOnFields("id").isEqualTo(expected);
    }
}
