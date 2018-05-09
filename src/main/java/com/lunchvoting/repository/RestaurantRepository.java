package com.lunchvoting.repository;

import com.lunchvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);
}
