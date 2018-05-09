package com.lunchvoting.service;

import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.VoteRepository;
import com.lunchvoting.util.VoteUtil;
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
        List<Vote> votes = voteRepository.findByUserIdBetween(userId, CURRENT_DAY_START_DATE_TIME, CURRENT_DAY_END_DATE_TIME);
        return votes == null ? null : DataAccessUtils.singleResult(votes);
    }
    @Override
    public List<Vote> getCurrentByRestaurantId(int restaurantId) {
        return voteRepository.findByRestaurantIdBetween(restaurantId, CURRENT_DAY_START_DATE_TIME, CURRENT_DAY_END_DATE_TIME);
    }

    @Override
    public Vote createOrUpdate(Vote vote) {
        Assert.notNull(vote, "Vote must not be null");

        Vote updatedVote;
        if ((updatedVote = getCurrentByUserId(vote.getUser().getId())) != null) {
            VoteUtil.checkUpdateTimeViolation(vote);
            vote.setId(updatedVote.getId());
        }

        return voteRepository.save(vote);
    }
}
