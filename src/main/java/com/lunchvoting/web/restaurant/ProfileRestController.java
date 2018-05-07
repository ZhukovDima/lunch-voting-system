package com.lunchvoting.web.restaurant;

import com.lunchvoting.model.Restaurant;
import com.lunchvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lunchvoting.web.restaurant.ProfileRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProfileRestController {

    public static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public Iterable<Restaurant> getAllWithCurrentMenu() {
        return restaurantRepository.getAllWithCurrentMenu();
    }
}
