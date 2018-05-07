package com.lunchvoting.repository;

import com.lunchvoting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Query("SELECT r FROM Restaurant r " +
                "LEFT JOIN FETCH r.menus m " +
                "LEFT JOIN FETCH m.items i " +
            "WHERE m.dateEntered=CURRENT_DATE")
    Set<Restaurant> getAllWithCurrentMenu();
}
