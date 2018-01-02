package com.simplevoting.menuvoting.repository;

import com.simplevoting.menuvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote);

    boolean delete(LocalDate date, int user_id);

    List<Vote> getAll();
}
