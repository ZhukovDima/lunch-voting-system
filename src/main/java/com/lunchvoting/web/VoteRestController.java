package com.lunchvoting.web;

import com.lunchvoting.AuthorizedUser;
import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.repository.RestaurantRepository;
import com.lunchvoting.service.VoteService;
import com.lunchvoting.to.VoteTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private MenuRepository menuRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<VoteTo> create(@RequestBody VoteTo voteTo) {
        int menuId = voteTo.getMenuId();
        checkNotFoundWithId(menuRepository.existsCurrentById(menuId), menuId);
        Vote voteFromTo = new Vote(null,
                menuRepository.getOne(menuId),
                AuthorizedUser.get().getUser());

        voteService.createOrUpdate(voteFromTo);

        return new ResponseEntity<>(voteTo, HttpStatus.OK);
    }
}
