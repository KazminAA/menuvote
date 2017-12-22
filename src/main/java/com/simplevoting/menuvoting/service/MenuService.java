package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static com.simplevoting.menuvoting.utils.DateUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateUtils.MIN_DATE;

public interface MenuService {
    Menu create(Menu menu);

    Menu update(Menu menu) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    Menu get(int id) throws NotFoundException;

    default List<Menu> getByDate(LocalDate date) {
        return getBetween(date, date);
    }

    List<Menu> getBetween(LocalDate start, LocalDate end);

    //order by curevote desc
    default List<Menu> getAll() {
        List<Menu> menus = getBetween(MIN_DATE, MAX_DATE);
        menus.sort(Comparator.comparing(Menu::getDate).reversed());
        return menus;
    }

    List<Menu> getBetweenWithVotes(LocalDate start, LocalDate end);
}
