package com.lunchvoting.service;

import com.lunchvoting.model.Vote;
import com.lunchvoting.util.exception.TimeViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.MenuTestData.R_1_MENU_1;
import static com.lunchvoting.UserTestData.*;
import static com.lunchvoting.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void testGetCurrentByUserId() {
        assertMatch(voteService.getCurrentByUserId(USER_1_ID).orElse(null), R1_USER1_VOTE1);
    }

    @Test
    public void testCreate() {
        Vote created = voteService.createOrUpdate(new Vote(null, R_1_MENU_1, USER2));
        assertMatch(voteService.getCurrentByUserId(USER_2_ID).orElse(null), created);
        assertThat(created.getStatus()).isEqualTo(Vote.Status.CREATED);
    }

    @Test
    public void testUpdate() {
        Vote updated = voteService.createOrUpdate(USER1_VOTE1_UPDATED_R1_TO_R2);
        assertThat(updated.getStatus()).isEqualTo(Vote.Status.UPDATED);
    }

    @Test(expected = TimeViolationException.class)
    public void testUpdateTimeViolation() {
        voteService.createOrUpdate(R1_USER1_VOTE1_TIME_VIOLATION);
    }
}