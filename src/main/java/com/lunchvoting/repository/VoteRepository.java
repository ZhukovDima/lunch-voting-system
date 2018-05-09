package com.lunchvoting.repository;

import com.lunchvoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.dateEntered BETWEEN :startDate AND :endDate")
    List<Vote> findByUserIdBetween(@Param("userId") int userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.dateEntered BETWEEN :startDate AND :endDate")
    List<Vote> findByRestaurantIdBetween(@Param("restaurantId") int restaurantId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Transactional
    @Override
    Vote save(Vote vote);
}
