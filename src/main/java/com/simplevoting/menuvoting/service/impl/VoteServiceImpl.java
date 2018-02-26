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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final String SAVE_EXCEPTION_MESSAGE = "Try to save vote for different user!";

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Vote create(Vote vote, int user_id) {
        checkVote(vote, user_id);
        if (voteRepository.checkVote(vote.getDate(), vote.getUserId())) {
            throw new IllegalStateException("On this date vote already exists.");
        }
        return voteRepository.save(vote);
    }

    @Override
    public void update(Vote vote, int user_id) throws EditClosedPeriodException, NotFoundException {
        checkVote(vote, user_id);
        if (checkPeriod(vote.getDate())) throw new EditClosedPeriodException();
        voteRepository.save(vote);
    }

    @Override
    public void delete(LocalDate date, int user_id) throws EditClosedPeriodException, NotFoundException {
        if (checkPeriod(date)) throw new EditClosedPeriodException();
        checkNotFoundWithId(voteRepository.delete(date, user_id), user_id);
    }

    @Override
    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    private boolean checkPeriod(LocalDate date) {
        return LocalDateTime.now().isAfter(LocalDateTime.of(date, LocalTime.of(11, 0)));
    }

    private void checkVote(Vote vote, int user_id) {
        Assert.notNull(vote, "Vote must not be null.");
        if (vote.getUserId() != user_id) throw new IllegalArgumentException(SAVE_EXCEPTION_MESSAGE);
    }

}
