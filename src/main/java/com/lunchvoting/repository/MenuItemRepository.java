package com.lunchvoting.repository;

import com.lunchvoting.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Override
    @Transactional
    MenuItem save(MenuItem menuItem);

    @Query("SELECT i FROM MenuItem i LEFT JOIN FETCH i.menu m WHERE m.id=:menuId")
    List<MenuItem> getAllByMenuId(@Param("menuId") int menuId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem i WHERE i.id=:id AND i.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);
}
