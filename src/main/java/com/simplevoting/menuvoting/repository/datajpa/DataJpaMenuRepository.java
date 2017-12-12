package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DataJpaMenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    @Override
    Menu save(Menu menu);

    @Query("DELETE FROM Menu m WHERE m.id = :id")
    int delete(@Param("id") int id);

    @Override
    @EntityGraph(attributePaths = {"menuList, votes"})
    Menu findOne(Integer id);

    @Query("SELECT m FROM Menu m WHERE m.date BETWEEN :start AND :end ORDER BY m.date DESC")
    List<Menu> findAllDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT m FROM Menu m WHERE m.date BETWEEN :start AND :end ORDER BY m.date DESC")
    @EntityGraph(attributePaths = {"menuList"})
    List<Menu> findAllDateBetweenWithList(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT m FROM Menu m WHERE m.date BETWEEN :start AND :end ORDER BY m.date DESC")
    @EntityGraph(attributePaths = {"menuVotes"})
    List<Menu> findAllDateBetweenWithVotes(@Param("start") LocalDate start, @Param("end") LocalDate end);
}