package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Menu;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.simplevoting.menuvoting.utils.DateUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateUtils.MIN_DATE;

public interface MenuService {
    Menu create(Menu menu);

    Menu update(Menu menu);

    void delete(int id);

    Menu get(int id);

    default List<Menu> getByDate(LocalDate date) {
        return getBetween(date, date);
    }

    List<Menu> getBetween(LocalDate start, LocalDate end);

    //order by curevote desc
    default List<Menu> getAll() {
        List<Menu> menus = getBetween(MIN_DATE, MAX_DATE);
        menus.sort(Comparator.comparing(Menu::getCurvotenum).reversed());
        return menus;
    }

    List<Menu> getBetweenWithVotes(LocalDate start, LocalDate end);
}
