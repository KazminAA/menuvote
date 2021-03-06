package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.MenuDish;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.simplevoting.menuvoting.DishTestData.DISH8;
import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.MenuTestData.assertMatch;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT1;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT2;
import static com.simplevoting.menuvoting.VoteTestData.*;
import static com.simplevoting.menuvoting.VoteTestData.assertMatch;
import static com.simplevoting.menuvoting.utils.DateTimeUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateTimeUtils.MIN_DATE;

public class MenuServiceImplTest extends AbstractWebAppTest {

    @Autowired
    MenuService service;

    @Test
    public void create() {
        Menu newMenu = new Menu(null, LocalDate.of(2017, 12, 19), RESTAURANT1, Collections.emptySet());
        Menu created = service.create(newMenu);
        assertMatch(service.getAll(), created, MENU5, MENU4, MENU6, MENU1, MENU2, MENU3);
    }

    @Test
    public void update() {
        Menu updated = new Menu(MENU3);
        updated.setDate(LocalDate.of(2011, 11, 11));
        updated.setRestaurant(RESTAURANT2);
        updated.getDishes().add(new MenuDish(updated, DISH8, 55.31));
        service.update(updated);
        assertMatch(service.getAll(), MENU5, MENU4, MENU6, MENU1, MENU2, updated);
    }

    @Test
    public void delete() {
        service.delete(MENU3.getId());
        assertMatch(service.getAll(), MENU5, MENU4, MENU6, MENU1, MENU2);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void get() {
        Menu menu = service.get(MENU2.getId());
        assertMatch(menu, MENU2);
        assertMatch(menu.getVotes(), VOTE2, VOTE1);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        Menu menu = service.get(1);
    }

    @Test
    public void getForRestaurant() {
        List<Menu> menus = service.getForRestaurant(RESTAURANT1.getId());
        assertMatch(menus, MENU4, MENU2);
    }

    @Test
    public void getBetween() {
        List<Menu> menus = service.getBetween(MIN_DATE, MAX_DATE);
        assertMatch(menus, MENU5, MENU4, MENU6, MENU1, MENU2, MENU3);
    }

    @Test
    public void getAll() {
        List<Menu> menus = service.getAll();
        assertMatch(menus, MENU5, MENU4, MENU6, MENU1, MENU2, MENU3);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Menu(null, null, RESTAURANT2, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Menu(null, LocalDate.of(2015, 12, 24), null, Collections.emptySet())), ConstraintViolationException.class);
    }
}