package com.lunchvoting.web;

import com.lunchvoting.model.User;
import com.lunchvoting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.*;
import static com.lunchvoting.web.UserRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserRestController {

    static final String REST_URL = "/rest/admin/users";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") int id) {
        return userRepository.findById(id)
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .orElseThrow(notFoundWithId(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userRepository.save(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable("id") int id) {
        assureIdConsistent(user, id);
        checkNotFoundWithId(userRepository.existsById(id), id);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        checkNotFoundWithId(userRepository.delete(id) != 0, id);
    }
}
