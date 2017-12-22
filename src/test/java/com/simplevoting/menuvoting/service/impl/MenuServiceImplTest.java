package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.MenuListTestData;
import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.simplevoting.menuvoting.MenuListTestData.MENU_SET1;
import static com.simplevoting.menuvoting.MenuListTestData.MENU_SET5;
import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.MenuTestData.assertMatch;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT1;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT2;
import static com.simplevoting.menuvoting.VoteTestData.*;
import static com.simplevoting.menuvoting.VoteTestData.assertMatch;
import static com.simplevoting.menuvoting.utils.DateUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateUtils.MIN_DATE;

public class MenuServiceImplTest extends AbstractServiceTest {

    @Autowired
    MenuService service;

    @Test
    public void create() {
        Menu newMenu = new Menu(null, LocalDate.of(2017, 12, 19), RESTAURANT1, 0, Collections.emptySet());
        Menu created = service.create(newMenu);
        assertMatch(service.getAll(), MENU2, MENU4, MENU5, MENU6, MENU1, created, MENU3);
    }

    @Test
    public void update() {
        Menu updated = new Menu(MENU3);
        updated.setDate(LocalDate.of(2011, 11, 11));
        updated.setCurvotenum(10);
        updated.setRestaurant(RESTAURANT2);
        service.update(updated);
        assertMatch(service.getAll(), updated, MENU2, MENU4, MENU5, MENU6, MENU1);
    }

    @Test
    public void delete() {
        service.delete(MENU3.getId());
        assertMatch(service.getAll(), MENU2, MENU4, MENU5, MENU6, MENU1);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void get() {
        Menu menu = service.get(MENU3.getId());
        assertMatch(menu, MENU3);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        Menu menu = service.get(1);
    }

    @Test
    public void getBetween() {
        List<Menu> menus = service.getBetween(MIN_DATE, MAX_DATE);
        assertMatch(menus, MENU4, MENU5, MENU6, MENU2, MENU1, MENU3);
    }

    @Test
    public void getAll() {
        List<Menu> menus = service.getAll();
        assertMatch(menus, MENU2, MENU4, MENU5, MENU6, MENU1, MENU3);
    }

    @Test
    public void getAllWithMenuList() {
        List<Menu> menus = service.getAll();
        MenuListTestData.assertMatch(menus.get(2).getMenuList(), MENU_SET5);
        MenuListTestData.assertMatch(menus.get(4).getMenuList(), MENU_SET1);
        Menu menu = service.get(MENU5.getId());
        MenuListTestData.assertMatch(menu.getMenuList(), MENU5.getMenuList());
    }

    @Test
    public void getBetweenWithVotes() {
        List<Menu> menus = service.getBetweenWithVotes(MIN_DATE, MAX_DATE);
        Menu menu = menus.get(3);
        assertMatch(menu.getVotes(), VOTE2, VOTE1);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Menu(null, null, RESTAURANT2, 0, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Menu(null, LocalDate.of(2015, 12, 24), null, 0, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Menu(null, LocalDate.of(2015, 12, 24), RESTAURANT2, null, Collections.emptySet())), ConstraintViolationException.class);
    }
}