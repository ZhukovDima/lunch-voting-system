package com.lunchvoting.web.menu;

import com.lunchvoting.model.Menu;
import com.lunchvoting.model.Restaurant;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.*;
import static com.lunchvoting.web.menu.MenuRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuRestController {

    static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/menus";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping
    public ResponseEntity<Menu> create(@PathVariable("restaurantId") int restaurantId, @RequestBody Menu menu) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.getOne(restaurantId), restaurantId);
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);

        URI uriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResourse).body(created);
    }
}
