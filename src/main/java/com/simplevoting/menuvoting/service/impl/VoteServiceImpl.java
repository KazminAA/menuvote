package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.repository.VoteRepository;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote create(Vote vote, int menu_id) {
        Assert.notNull(vote, "Vote must not be null.");
        return voteRepository.save(vote);
    }

    @Override
    public Vote update(Vote vote) throws EditClosedPeriodException, NotFoundException {
        checkPeriod(vote.getDate());
        return checkNotFoundWithId(vote, vote.getUserId());
    }

    @Override
    public void delete(LocalDate date, int user_id) throws EditClosedPeriodException, NotFoundException {
        checkPeriod(date);
        checkNotFoundWithId(voteRepository.delete(date, user_id), user_id);
    }

    private void checkPeriod(LocalDate date) {
        if (
                (LocalTime.now().isAfter(LocalTime.of(11, 00))) ||
                        LocalDate.now().isAfter(date)) {
            throw new EditClosedPeriodException();
        }
    }

}
