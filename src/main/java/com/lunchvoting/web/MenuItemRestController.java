package com.lunchvoting.web;

import com.lunchvoting.model.MenuItem;
import com.lunchvoting.repository.MenuItemRepository;
import com.lunchvoting.repository.MenuRepository;
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
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuItemRestController extends AbstractController {

    static final String REST_URL = "/rest/restaurants/{restaurantId}/menus/{menuId}/items";
    static final String ITEMS_REST_URL = "/rest/restaurants/menus/items";

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = REST_URL)
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem, @PathVariable("restaurantId") int restaurantId,
                                           @PathVariable("menuId") int menuId) {
        log.info("create {} for menu {}", menuItem, menuId);
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
    @PutMapping(value = REST_URL + "/{id}")
    public void update(@RequestBody MenuItem menuItem, @PathVariable("restaurantId") int restaurantId,
                           @PathVariable("menuId") int menuId, @PathVariable("id") int id) {
        log.info("update {} for menu {}", menuItem, menuId);
        assureIdConsistent(menuItem, id);
        checkNotFoundWithId(menuRepository.existsById(menuId), menuId);
        menuItem.setMenu(menuRepository.getOne(menuId));
        menuItemRepository.save(menuItem);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = ITEMS_REST_URL + "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete item {}", id);
        checkNotFoundWithId(menuItemRepository.delete(id) != 0, id);
    }
}
