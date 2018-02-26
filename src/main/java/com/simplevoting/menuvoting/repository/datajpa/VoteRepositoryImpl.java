package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    @Autowired
    DataJpaVoteRepository dataJpaVoteRepository;

    @Override
    public Vote save(Vote vote) {
        return dataJpaVoteRepository.save(vote);
    }

    @Override
    public boolean delete(LocalDate date, int user_id) {
        return dataJpaVoteRepository.delete(date, user_id) != 0;
    }

    @Override
    public List<Vote> getAll() {
        return dataJpaVoteRepository.findAll();
    }

    @Override
    public boolean checkVote(LocalDate date, int user_id) {
        return dataJpaVoteRepository.get(date, user_id) != null;
    }
}
