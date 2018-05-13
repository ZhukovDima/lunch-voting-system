package com.lunchvoting.repository;

import com.lunchvoting.MenuItemTestData;
import com.lunchvoting.model.MenuItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.MenuItemTestData.*;

public class MenuItemRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuItemRepository repository;

    @Test
    public void testSave() {
        MenuItem newItem = MenuItemTestData.getNew();
        MenuItem created = repository.save(newItem);
        newItem.setId(created.getId());
        assertMatch(repository.getAllByMenuId(R_1_MENU_1_ID), M1_ITEM_1, M1_ITEM_2, M1_ITEM_3, created);
    }

    @Test
    public void testGetAllByMenuId() {
        assertMatch(repository.getAllByMenuId(R_1_MENU_1_ID), M1_ITEM_1, M1_ITEM_2, M1_ITEM_3);
    }
}