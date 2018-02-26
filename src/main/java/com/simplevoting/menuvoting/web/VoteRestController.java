package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.AuthorizedUser;
import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.to.VoteTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";
    private static final Logger log = getLogger(VoteRestController.class);

    private final VoteService voteService;
    private final MenuService menuService;
    private final UserService userService;

    @Autowired
    public VoteRestController(VoteService voteService, MenuService menuService, UserService userService) {
        this.voteService = voteService;
        this.menuService = menuService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@RequestParam("menu_id") int menu_id) {
        Menu menu = menuService.get(menu_id);
        User user = userService.get(AuthorizedUser.id());
        Vote vote = new Vote(menu.getDate(), user, menu);
        log.info("create {}", vote);
        vote = voteService.create(vote, AuthorizedUser.id());
        return ResponseEntity.ok(vote);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo) {
        Vote vote = new Vote(voteTo.getDate(),
                userService.get(voteTo.getUserId()),
                menuService.get(voteTo.getMenu_id()));
        log.info("update {}", vote);
        voteService.update(vote, AuthorizedUser.id());
    }

    @DeleteMapping(value = "/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("date") LocalDate date) {
        log.info("delete vote on date={} for user with id={}", date, AuthorizedUser.id());
        voteService.delete(date, AuthorizedUser.id());
    }
}
