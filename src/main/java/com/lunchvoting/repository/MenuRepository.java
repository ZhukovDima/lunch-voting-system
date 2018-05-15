package com.lunchvoting.repository;

import com.lunchvoting.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Override
    Menu save(Menu menu);

    @EntityGraph(attributePaths = {"items", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.dateEntered=:date")
    List<Menu> getAllByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"items", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.id=:id")
    Menu get(@Param("id") int id);

    @EntityGraph(attributePaths = {"items", "restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM Menu m WHERE m.dateEntered=:date AND m.restaurant.id=:restaurantId")
    Menu getByRestaurantId(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT " +
                "CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Menu m WHERE m.id=:id AND m.dateEntered=CURRENT_DATE")
    boolean existsCurrentById(@Param("id") int id);
}
