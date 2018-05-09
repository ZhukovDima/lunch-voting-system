package com.lunchvoting.service;

import com.lunchvoting.model.Vote;

import java.util.List;

public interface VoteService {

    Vote getCurrentByUserId(int userId);

    List<Vote> getCurrentByRestaurantId(int restaurantId);

    Vote createOrUpdate(Vote vote);
}
