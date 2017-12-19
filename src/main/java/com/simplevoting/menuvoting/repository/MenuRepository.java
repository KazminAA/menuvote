package com.simplevoting.menuvoting.repository;

import com.simplevoting.menuvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {

    Menu save(Menu menu);

    boolean delete(int id);

    Menu get(int id);

    default List<Menu> getByDate(LocalDate date) {
        return getBetween(date, date);
    }

    List<Menu> getBetween(LocalDate start, LocalDate end);

    List<Menu> getBetweenWithList(LocalDate start, LocalDate end);

    List<Menu> getBetweenWithVotes(LocalDate start, LocalDate end);

}
