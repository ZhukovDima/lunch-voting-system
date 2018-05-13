package com.lunchvoting.repository;

import com.lunchvoting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Override
    Menu save(Menu menu);

    @Query("SELECT m FROM Menu m " +
                "LEFT JOIN FETCH m.restaurant r " +
                "LEFT JOIN FETCH m.items i " +
            "WHERE m.dateEntered=:date")
    Set<Menu> getAllByDate(@Param("date") LocalDate date);

    @Query("SELECT m FROM Menu m " +
                "LEFT JOIN FETCH m.restaurant r " +
                "LEFT JOIN FETCH m.items i " +
            "WHERE m.id=:id")
    Menu get(@Param("id") int id);

    @Query("SELECT m FROM Menu m " +
                "LEFT JOIN FETCH m.restaurant r " +
                "LEFT JOIN FETCH m.items i " +
            "WHERE m.dateEntered=:date AND r.id=:restaurantId")
    Menu getByRestaurantId(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);
}
