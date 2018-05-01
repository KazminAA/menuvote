package com.simplevoting.menuvoting.service.impl;

import org.junit.Test;

import java.util.Arrays;

import static com.simplevoting.menuvoting.MenuDishTestData.*;

public class MenuListServiceImplTest extends AbstractWebAppTest {

    @Test
    public void addDish() {
        assertMatch(MENU_DISH1, MENU_DISH1);
    }

    @Test
    public void removeDish() {
        assertMatch(Arrays.asList(MENU_DISH1, MENU_DISH2, MENU_DISH3), MENU_DISH1, MENU_DISH2, MENU_DISH3);
    }
}