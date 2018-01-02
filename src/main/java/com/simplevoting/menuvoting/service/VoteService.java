package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote create(Vote vote, int user_id) throws NotFoundException;

    void update(Vote vote, int user_id) throws EditClosedPeriodException, NotFoundException;

    void delete(LocalDate date, int user_id) throws EditClosedPeriodException, NotFoundException;

    List<Vote> getAll();
}
