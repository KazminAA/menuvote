package com.simplevoting.menuvoting.repository;

import com.simplevoting.menuvoting.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static com.simplevoting.menuvoting.utils.DateUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateUtils.MIN_DATE;

public interface MenuRepository {

    Menu save(Menu menu);

    boolean delete(int id);

    Menu get(int id);

    default List<Menu> getByDate(LocalDate date) {
        return getBetween(date, date);
    }

    List<Menu> getBetween(LocalDate start, LocalDate end);

    //order by curevote desc
    default List<Menu> getAll() {
        return getBetween(MIN_DATE, MAX_DATE);
    }

    List<Menu> getBetweenWithList(LocalDate start, LocalDate end);

    List<Menu> getBetweenWithVotes(LocalDate start, LocalDate end);

}
