package com.lunchvoting.web;

import com.lunchvoting.AuthorizedUser;
import com.lunchvoting.model.Vote;
import com.lunchvoting.repository.MenuRepository;
import com.lunchvoting.service.VoteService;
import com.lunchvoting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.lunchvoting.util.ValidationUtil.checkNotFoundWithId;
import static com.lunchvoting.web.VoteRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VoteRestController extends AbstractController {

    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteService voteService;

    @Autowired
    private MenuRepository menuRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{menuId}")
    public ResponseEntity<?> create(@PathVariable("menuId") int menuId) {
        checkNotFoundWithId(menuRepository.existsCurrentById(menuId), menuId);

        Vote vote = voteService.createOrUpdate(new Vote(null, menuRepository.getOne(menuId),
                AuthorizedUser.get().getUser()));

        switch (vote.getStatus()) {
            case CREATED:
                return ResponseEntity.status(HttpStatus.CREATED).build();
            case UPDATED:
                return ResponseEntity.status(HttpStatus.OK).build();
            default:
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Vote> getCurrent() {
        return voteService.getCurrentByUserId(AuthorizedUser.id())
                .map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Vote has not been found for " + AuthorizedUser.get().getUsername()));
    }
}
