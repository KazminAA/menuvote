package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.simplevoting.menuvoting.MenuTestData.MENU1;
import static com.simplevoting.menuvoting.MenuTestData.MENU5;
import static com.simplevoting.menuvoting.UserTestData.USER1;
import static com.simplevoting.menuvoting.UserTestData.USER2;
import static com.simplevoting.menuvoting.VoteTestData.*;

public class VoteServiceImplTest extends AbstractWebAppTest {
    @Autowired
    VoteService voteService;

    @Test
    public void create() {
        Vote newVote = getNewTomorrowVote();
        Vote created = voteService.create(newVote, USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void createForDiffUser() {
        thrown.expect(IllegalArgumentException.class);
        voteService.create(getNewTomorrowVote(), USER2.getId());
    }

    @Test
    public void createSecondVote() {
        Vote secondVote = new Vote(LocalDate.of(2017, 11, 1), USER1, MENU5);
        voteService.create(secondVote, USER1.getId());
        assertMatch(voteService.getAll(), VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void update() {
        Vote created = voteService.create(getNewTomorrowVote(), USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
        created.setMenu(MENU1);
        voteService.update(created, USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void updateOnClosedPeriod() {
        thrown.expect(EditClosedPeriodException.class);
        Vote updated = new Vote(VOTE1);
        updated.setMenu(MENU5);
        voteService.update(updated, updated.getUserId());
    }

    @Test
    public void delete() {
        Vote created = voteService.create(getNewTomorrowVote(), USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
        voteService.delete(created.getDate(), created.getUserId());
        assertMatch(voteService.getAll(), VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void deleteOnClosedPeriod() {
        thrown.expect(EditClosedPeriodException.class);
        voteService.delete(VOTE1.getDate(), VOTE1.getUserId());
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        Vote deleted = getNewTomorrowVote();
        voteService.delete(deleted.getDate(), deleted.getUserId());
    }
}