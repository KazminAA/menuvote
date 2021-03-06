package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static com.simplevoting.menuvoting.utils.DateTimeUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateTimeUtils.MIN_DATE;

public interface MenuService {
    Menu create(Menu menu) throws IllegalArgumentException;

    Menu update(Menu menu);

    /*Menu addDish(Integer menuId, DishTo[] dishesToAdd);*/

    void delete(int id) throws NotFoundException;

    Menu get(int id) throws NotFoundException;

    List<Menu> getForRestaurant(int restaurant_id);

    default List<Menu> getByDate(LocalDate date) {
        return getBetween(date, date);
    }

    List<Menu> getBetween(LocalDate start, LocalDate end);

    default List<Menu> getAll() {
        List<Menu> menus = getBetween(MIN_DATE, MAX_DATE);
        return menus;
    }

}
