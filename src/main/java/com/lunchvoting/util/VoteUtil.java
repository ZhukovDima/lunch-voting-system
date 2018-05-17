package com.lunchvoting.util;

import com.lunchvoting.model.Vote;
import com.lunchvoting.util.exception.TimeViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteUtil {

    public static final LocalDateTime VOTE_CLOSING_TIME =
            LocalDateTime.of(LocalDate.now(), LocalTime.of(11,0, 0));

    public static void checkUpdateTimeViolation(Vote vote) {
        if (vote.getDateEntered().compareTo(VOTE_CLOSING_TIME) >= 0)
            throw new TimeViolationException("Vote may only be changed until " + VOTE_CLOSING_TIME);
    }
}
