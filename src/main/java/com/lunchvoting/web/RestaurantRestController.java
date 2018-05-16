package com.lunchvoting.web;

import com.lunchvoting.model.Restaurant;
import com.lunchvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.*;
import static com.lunchvoting.web.RestaurantRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantRestController extends AbstractController {

    static final String REST_URL = "/rest/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @Valid @RequestBody Restaurant restaurant) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.existsById(id), id);
        restaurantRepository.save(restaurant);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @GetMapping
    public ResponseEntity<Iterable<Restaurant>> getAll() {
        log.info("getAll");
        return new ResponseEntity<>(restaurantRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
         return restaurantRepository.findById(id)
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .orElseThrow(notFoundWithId(id));
    }
}
