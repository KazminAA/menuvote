package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.service.VoteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.simplevoting.menuvoting.MenuTestData.MENU6;
import static com.simplevoting.menuvoting.UserTestData.USER1;
import static com.simplevoting.menuvoting.VoteTestData.VOTE5;
import static com.simplevoting.menuvoting.VoteTestData.assertMatch;

public class VoteServiceImplTest extends AbstractServiceTest {
    @Autowired
    VoteService voteService;
    @Autowired
    MenuService menuService;

    @Test
    public void create() {
        Vote newVote = new Vote(LocalDate.of(2017, 11, 2), USER1, MENU6);
        Vote created = voteService.create(newVote, USER1.getId());
        Menu menu5 = menuService.get(MENU6.getId());
        assertMatch(menu5.getVotes(), VOTE5, created);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}