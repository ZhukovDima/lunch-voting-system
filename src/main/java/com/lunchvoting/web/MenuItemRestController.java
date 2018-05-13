package com.lunchvoting.web;

import com.lunchvoting.model.MenuItem;
import com.lunchvoting.repository.MenuItemRepository;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.*;
import static com.lunchvoting.util.ValidationUtil.checkNotFoundWithId;
import static com.lunchvoting.web.MenuItemRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuItemRestController {

    static final String REST_URL = "/rest/restaurants/{restaurantId}/menus/{menuId}/items";

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem, @PathVariable("restaurantId") int restaurantId,
                                           @PathVariable("menuId") int menuId) {
        MenuItem created = menuRepository.findById(menuId)
                .map(menu -> {
                    assureIdConsistent(menu.getRestaurant(), restaurantId);
                    menuItem.setMenu(menu);
                    return menuItemRepository.save(menuItem);
                })
                .orElseThrow(notFoundWithId(menuId));

        URI uriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{menuItemId}")
                .buildAndExpand(restaurantId, menuId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResourse).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public MenuItem update(@RequestBody MenuItem menuItem, @PathVariable("restaurantId") int restaurantId,
                           @PathVariable("menuId") int menuId, @PathVariable("id") int id) {
        assureIdConsistent(menuItem, id);
        checkNotFoundWithId(menuRepository.existsById(id), id);

        return menuItemRepository.findById(id)
                .map(i -> {
                    menuItem.setMenu(menuRepository.getOne(menuId));
                    return menuItemRepository.save(menuItem);
                })
                .orElseThrow(notFoundWithId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("restaurantId") int restaurantId,
                       @PathVariable("menuId") int menuId, @PathVariable("id") int id) {
        checkNotFoundWithId(menuItemRepository.existsById(id), id);
        menuItemRepository.deleteById(id);
    }
}
