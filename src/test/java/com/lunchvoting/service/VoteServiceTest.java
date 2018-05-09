package com.lunchvoting.service;

import com.lunchvoting.UserTestData;
import com.lunchvoting.model.Vote;
import com.lunchvoting.util.exception.TimeViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.RestaurantTestData.*;
import static com.lunchvoting.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void testGetCurrentByUserId() {
        assertMatch(voteService.getCurrentByUserId(UserTestData.USER_1_ID), R1_USER1_VOTE1);
    }

    @Test
    public void testGetCurrentByRestaurantId() {
        assertMatch(voteService.getCurrentByRestaurantId(RESTAURANT_1_ID), R1_USER1_VOTE1);
    }

    @Test
    public void testCreate() {
        Vote newVote = getNewByUser2();
        Vote created = voteService.createOrUpdate(newVote);
        newVote.setId(created.getId());
        assertMatch(voteService.getCurrentByRestaurantId(RESTAURANT_1_ID), R1_USER1_VOTE1, created);
    }

    @Test
    public void testUpdate() {
        assertMatch(voteService.getCurrentByRestaurantId(RESTAURANT_1_ID), R1_USER1_VOTE1);
        assertThat(voteService.getCurrentByRestaurantId(RESTAURANT_2_ID)).isEmpty();

        voteService.createOrUpdate(USER1_VOTE1_UPDATED_R1_TO_R2);

        assertThat(voteService.getCurrentByRestaurantId(RESTAURANT_1_ID)).isEmpty();
        assertMatch(voteService.getCurrentByRestaurantId(RESTAURANT_2_ID), R1_USER1_VOTE1);
    }

    @Test(expected = TimeViolationException.class)
    public void testUpdateTimeViolation() {
        voteService.createOrUpdate(R1_USER1_VOTE1_TIME_VIOLATION);
    }
}