package com.lunchvoting.service;

import com.lunchvoting.MenuTestData;
import com.lunchvoting.model.Vote;
import com.lunchvoting.util.exception.TimeViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.lunchvoting.UserTestData.USER_1_ID;
import static com.lunchvoting.VoteTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    public void testGetCurrentByUserId() {
        assertMatch(voteService.getCurrentByUserId(USER_1_ID), R1_USER1_VOTE1);
    }

    @Test
    public void testCreate() {
        Vote created = voteService.createOrUpdate(getNew());
        assertMatch(voteService.getCurrentByUserId(USER_1_ID), created);
        assertThat(created.getStatus()).isEqualTo(Vote.Status.CREATED);
    }

    @Test
    public void testUpdate() {
        Vote created = voteService.createOrUpdate(R1_USER1_VOTE1);
        assertThat(created.getStatus()).isEqualTo(Vote.Status.CREATED);

        Vote updated = voteService.createOrUpdate(USER1_VOTE1_UPDATED_R1_TO_R2);
        assertThat(updated.getStatus()).isEqualTo(Vote.Status.UPDATED);
    }

    @Test(expected = TimeViolationException.class)
    public void testUpdateTimeViolation() {
        Vote created = voteService.createOrUpdate(R1_USER1_VOTE1);
        assertThat(created.getStatus()).isEqualTo(Vote.Status.CREATED);

        voteService.createOrUpdate(R1_USER1_VOTE1_TIME_VIOLATION);
    }
}