package com.lunchvoting.web;

import com.lunchvoting.model.Menu;
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
import java.time.LocalDate;

import static com.lunchvoting.util.ValidationUtil.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuRestController {

    static final String REST_URL = "/rest/restaurants/menus";
    static final String RESTAURANT_MENU_REST_URL = "/rest/restaurants/{restaurantId}/menus";

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = RESTAURANT_MENU_REST_URL)
    public ResponseEntity<Menu> create(@RequestBody Menu menu, @PathVariable("restaurantId") int restaurantId) {
       Menu created = restaurantRepository.findById(restaurantId).map(r -> {
            menu.setRestaurant(r);
            return menuRepository.save(menu);
        }).orElseThrow(notFoundWithId(restaurantId));


        URI uriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_MENU_REST_URL + "/{menuId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResourse).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = RESTAURANT_MENU_REST_URL + "/{id}")
    public Menu update(@RequestBody Menu menu, @PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        checkNotFoundWithId(restaurantRepository.existsById(restaurantId), restaurantId);
        assureIdConsistent(menu, id);
        return menuRepository.findById(id).map(m -> {

            menu.setRestaurant(restaurantRepository.getOne(restaurantId));
            return menuRepository.save(menu);
        }).orElseThrow(notFoundWithId(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = RESTAURANT_MENU_REST_URL + "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        checkNotFoundWithId(menuRepository.existsById(id), id);
        menuRepository.deleteById(id);
    }

    @GetMapping(value = REST_URL)
    public ResponseEntity<Iterable<Menu>> getAllByDate(@RequestParam(value = "date", required = false) LocalDate date) {
        return new ResponseEntity<>(menuRepository.getAllByDate(date != null ? date : LocalDate.now()), HttpStatus.OK);
    }

    @GetMapping(value = RESTAURANT_MENU_REST_URL + "/{id}")
    public ResponseEntity<Menu> get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        checkNotFoundWithId(restaurantRepository.existsById(restaurantId), restaurantId);
        return menuRepository.findById(id)
                .map(m -> {
                    assureIdConsistent(m.getRestaurant(), restaurantId);
                    return new ResponseEntity<>(m, HttpStatus.OK);
                })
                .orElseThrow(notFoundWithId(id));
    }

    @GetMapping(value = RESTAURANT_MENU_REST_URL)
    public ResponseEntity<Menu> getByRestaurantId(@PathVariable("restaurantId") int restaurantId, @RequestParam(value = "date", required = false) LocalDate date) {
        checkNotFoundWithId(restaurantRepository.existsById(restaurantId), restaurantId);
        return new ResponseEntity<>(menuRepository.getByRestaurantId(restaurantId, date != null ? date : LocalDate.now()), HttpStatus.OK);
    }
}
