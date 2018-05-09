package com.lunchvoting.web;

import com.lunchvoting.AuthorizedUser;
import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.RestaurantRepository;
import com.lunchvoting.service.VoteService;
import com.lunchvoting.to.VoteTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.lunchvoting.util.ValidationUtil.checkNotFoundWithId;
import static com.lunchvoting.web.VoteRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VoteRestController {

    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<VoteTo> create(@RequestBody VoteTo voteTo) {
        Vote voteFromTo = new Vote(null,
                checkNotFoundWithId(restaurantRepository.getOne(voteTo.getRestaurant().getId()), voteTo.getRestaurant().getId()),
                AuthorizedUser.get().getUser());
        Vote vote = voteService.createOrUpdate(voteFromTo);
        voteTo.setId(vote.getId());
        URI urlOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(voteTo.getId()).toUri();

        return ResponseEntity.created(urlOfNewResource).body(voteTo);
    }
}
