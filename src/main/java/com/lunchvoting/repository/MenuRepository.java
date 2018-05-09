package com.lunchvoting.repository;

import com.lunchvoting.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Override
    Menu save(Menu menu);

    @Query("SELECT m FROM Menu m " +
                "LEFT JOIN FETCH m.restaurant r " +
                "LEFT JOIN FETCH m.items i " +
            "WHERE m.dateEntered=CURRENT_DATE")
    Set<Menu> getAllCurrent();
}
