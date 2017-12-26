package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public interface DataJpaVoteRepository extends JpaRepository<Vote, VoteId> {
    @Override
    Vote save(Vote vote);

    @Query("DELETE FROM Vote v WHERE v.id.date=:date AND v.id.user_id=:user_id")
    int delete(@Param("date") LocalDate date, @Param("user_id") int user_id);
}
