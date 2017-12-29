package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.time.LocalDate;

public interface VoteService {
    Vote create(Vote vote, int menu_id) throws NotFoundException;

    Vote update(Vote vote) throws EditClosedPeriodException, NotFoundException;

    void delete(LocalDate date, int user_id) throws EditClosedPeriodException, NotFoundException;
}
