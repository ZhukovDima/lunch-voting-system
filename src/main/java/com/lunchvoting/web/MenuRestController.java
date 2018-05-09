package com.lunchvoting.web;

import com.lunchvoting.model.Menu;
import com.lunchvoting.model.Restaurant;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuRestController {

    static final String MENU_REST_URL = "/rest/menus";
    static final String RESTAURANT_MENU_REST_URL = "/rest/restaurants/{restaurantId}/menus";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = RESTAURANT_MENU_REST_URL)
    public ResponseEntity<Menu> create(@PathVariable("restaurantId") int restaurantId, @RequestBody Menu menu) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.getOne(restaurantId), restaurantId);
        menu.setRestaurant(restaurant);
        Menu created = menuRepository.save(menu);

        URI uriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_MENU_REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResourse).body(created);
    }

    @GetMapping(value = MENU_REST_URL)
    public Iterable<Menu> getAllCurrent() {
        return menuRepository.getAllCurrent();
    }
}
