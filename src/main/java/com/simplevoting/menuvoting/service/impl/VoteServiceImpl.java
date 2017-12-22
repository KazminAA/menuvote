package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.repository.MenuRepository;
import com.simplevoting.menuvoting.repository.VoteRepository;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, MenuRepository menuRepository) {
        this.voteRepository = voteRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Vote create(Vote vote, int menu_id) {
        Assert.notNull(vote, "Vote must not be null.");
        return voteRepository.save(vote);
    }

    @Override
    public Vote update(Vote vote) throws EditClosedPeriodException, NotFoundException {
        if (
                (LocalTime.now().isAfter(LocalTime.of(11, 00))) ||
                        LocalDate.now().isAfter(vote.getDate())) {
            throw new EditClosedPeriodException();
        }
        //TODO votes srvice delete
        return null;
    }

    @Override
    public Vote delete(LocalDate date, int user_id) {
        //TODO votes service delete
        return null;
    }
}
