package com.lunchvoting.repository;

import com.lunchvoting.model.Restaurant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}