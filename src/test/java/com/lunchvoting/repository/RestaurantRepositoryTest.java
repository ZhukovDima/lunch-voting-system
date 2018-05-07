package com.lunchvoting.repository;

import com.lunchvoting.MenuTestData;
import com.lunchvoting.model.Restaurant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.lunchvoting.RestaurantTestData.*;

public class RestaurantRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testSave() {
        Restaurant newRestaurant = getNew();
        Restaurant restaurantCreated = restaurantRepository.save(newRestaurant);
        newRestaurant.setId(restaurantCreated.getId());
        assertMatch(restaurantRepository.findAll(), RESTAURANT1, RESTAURANT2, newRestaurant);
    }

    @Test
    public void testGetAllWithCurrentMenu() {
        List<Restaurant> restaurants = new ArrayList<>(restaurantRepository.getAllWithCurrentMenu());
        assertMatch(restaurants, RESTAURANT1, RESTAURANT2);
        MenuTestData.assertMatch(restaurants.get(0).getMenus(), MenuTestData.R_1_MENU_1);
        MenuTestData.assertMatch(restaurants.get(1).getMenus(), MenuTestData.R_2_MENU_1);
    }
}