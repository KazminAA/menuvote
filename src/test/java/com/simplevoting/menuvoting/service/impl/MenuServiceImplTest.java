package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT1;

public class MenuServiceImplTest extends AbstractServiceTest {

    @Autowired
    MenuService service;

    @Test
    public void create() {
        Menu created = new Menu(null, new Date(LocalDate.of(2017, 12, 19).toEpochDay()), RESTAURANT1, 0, Collections.emptySet());
        System.out.println(created);
        service.create(created);
        assertMatch(service.getAll(), MENU1, MENU2, MENU3, MENU4, MENU5, MENU6, created);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getBetween() {
    }

    @Test
    public void getBetweenWithList() {
    }

    @Test
    public void getBetweenWithVotes() {
    }
}