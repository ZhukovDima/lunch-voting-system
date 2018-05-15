package com.lunchvoting.service;

import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.VoteRepository;
import com.lunchvoting.util.VoteUtil;
import com.lunchvoting.util.exception.TimeViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.lunchvoting.util.DateTimeUtil.CURRENT_DAY_END_DATE_TIME;
import static com.lunchvoting.util.DateTimeUtil.CURRENT_DAY_START_DATE_TIME;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote getCurrentByUserId(int userId) {
        return voteRepository.findByUserIdBetween(userId, CURRENT_DAY_START_DATE_TIME, CURRENT_DAY_END_DATE_TIME);
    }


    @Override
    public Vote createOrUpdate(Vote vote) {
        Assert.notNull(vote, "Vote must not be null");
        Vote currentVote;
        if((currentVote = getCurrentByUserId(vote.getUser().getId())) != null){
            VoteUtil.checkUpdateTimeViolation(vote);
            vote.setId(currentVote.getId());
            vote = voteRepository.save(vote);
            vote.setStatus(Vote.Status.UPDATED);
        } else {
            vote = voteRepository.save(vote);
            vote.setStatus(Vote.Status.CREATED);
        }

        return vote;
    }
}
