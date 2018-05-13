package com.lunchvoting.repository;

import com.lunchvoting.MenuTestData;
import com.lunchvoting.model.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.lunchvoting.MenuItemTestData.*;
import static com.lunchvoting.MenuTestData.*;
import static com.lunchvoting.RestaurantTestData.*;

public class MenuRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testSave() {
        Menu newMenu = MenuTestData.getNew();
        Menu created = menuRepository.save(newMenu);
        newMenu.setId(created.getId());
        assertMatch(menuRepository.findAll(), R_1_MENU_1, R_2_MENU_1, newMenu);
    }

    @Test
    public void testGetAllByDate() {
        List<Menu> menusByDate = new ArrayList<>(menuRepository.getAllByDate(LocalDate.now()));
        assertMatch(menusByDate, R_1_MENU_1, R_2_MENU_1);
        assertMatch(menusByDate.get(0).getRestaurant(), RESTAURANT1);
        assertMatch(menusByDate.get(1).getRestaurant(), RESTAURANT2);
        assertMatch(menusByDate.get(0).getItems(), M1_ITEM_1, M1_ITEM_2, M1_ITEM_3);
        assertMatch(menusByDate.get(1).getItems(), M2_ITEM_1, M2_ITEM_2, M2_ITEM_3);
    }

    @Test
    public void testGet() {
        Menu menu= menuRepository.get(R_1_MENU_1_ID);
        assertMatch(menu, R_1_MENU_1);
        assertMatch(menu.getRestaurant(), RESTAURANT1);
        assertMatch(menu.getItems(), M1_ITEM_1, M1_ITEM_2, M1_ITEM_3);
    }
}