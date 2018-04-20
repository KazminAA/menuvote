package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.repository.VoteRepository;
import com.simplevoting.menuvoting.service.VoteService;
import com.simplevoting.menuvoting.utils.MessageUtil;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final MessageUtil messageUtil;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, MessageUtil messageUtil) {
        this.voteRepository = voteRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public Vote create(Vote vote, int user_id) {
        checkVote(vote, user_id);
        if (voteRepository.checkVote(vote.getDate(), vote.getUserId()))
            throw new DataIntegrityViolationException("VOTE_DATE_USER_IDX");
        return voteRepository.save(vote);
    }

    @Override
    public void update(Vote vote, int user_id) throws EditClosedPeriodException, NotFoundException {
        checkVote(vote, user_id);
        if (checkPeriod(vote.getDate())) throw new EditClosedPeriodException(
                messageUtil.getMessage("exception.voteDate.closed", vote.getDate().toString()));
        voteRepository.save(vote);
    }

    @Override
    public void delete(LocalDate date, int user_id) throws EditClosedPeriodException, NotFoundException {
        if (checkPeriod(date)) throw new EditClosedPeriodException(
                messageUtil.getMessage("exception.voteDate.closed", date.toString()));
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
        Assert.notNull(vote, messageUtil.getMessage("exception.nullvote"));
        if (vote.getUserId() != user_id)
            throw new IllegalArgumentException(messageUtil.getMessage("exception.save.vote"));
    }

}
