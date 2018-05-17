package com.lunchvoting.service;

import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.VoteRepository;
import com.lunchvoting.util.exception.TimeViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

import static com.lunchvoting.util.DateTimeUtil.CURRENT_DAY_END_DATE_TIME;
import static com.lunchvoting.util.DateTimeUtil.CURRENT_DAY_START_DATE_TIME;
import static com.lunchvoting.util.VoteUtil.*;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Optional<Vote> getCurrentByUserId(int userId) {
        return voteRepository.findByUserIdBetween(userId, CURRENT_DAY_START_DATE_TIME, CURRENT_DAY_END_DATE_TIME);
    }

    private Vote create(Vote vote) {
        vote.setStatus(Vote.Status.CREATED);
        return voteRepository.save(vote);
    }

    private Vote update(Vote vote, int id) throws TimeViolationException {
        checkUpdateTimeViolation(vote);

        vote.setId(id);
        vote = voteRepository.save(vote);
        vote.setStatus(Vote.Status.UPDATED);
        return vote;
    }

    @Override
    public Vote createOrUpdate(Vote vote) throws TimeViolationException {
        Assert.notNull(vote, "Vote must not be null");

        return getCurrentByUserId(vote.getUser().getId())
                .map(v -> update(vote, v.getId()))
                .orElse(create(vote));
    }
}
