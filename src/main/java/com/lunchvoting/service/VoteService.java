package com.lunchvoting.service;

import com.lunchvoting.model.Vote;
import com.lunchvoting.util.exception.TimeViolationException;

import java.util.Optional;

public interface VoteService {

    Optional<Vote> getCurrentByUserId(int userId);

    Vote createOrUpdate(Vote vote) throws TimeViolationException;
}
