package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.AuthorizedUser;
import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.to.VoteTo;
import com.simplevoting.menuvoting.utils.VoteUtils;
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
    private final VoteUtils voteUtils;

    @Autowired
    public VoteRestController(VoteService voteService, VoteUtils voteUtils) {
        this.voteService = voteService;
        this.voteUtils = voteUtils;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@RequestParam("menu_id") int menu_id) {
        Vote vote = voteUtils.getVote(menu_id, AuthorizedUser.id());
        log.info("create {}", vote);
        vote = voteService.create(vote, AuthorizedUser.id());
        return ResponseEntity.ok(vote);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo) {
        Vote vote = voteUtils.getFromTo(voteTo);
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
