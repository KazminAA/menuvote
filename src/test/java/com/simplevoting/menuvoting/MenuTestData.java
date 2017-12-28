package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static com.simplevoting.menuvoting.MenuDishTestData.*;
import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {
    public static final Menu MENU1 = new Menu(START_SEQ + 6, LocalDate.of(2017, 11, 1), RESTAURANT2, Collections.emptySet());
    public static final Menu MENU2 = new Menu(START_SEQ + 7, LocalDate.of(2017, 11, 1), RESTAURANT1, Collections.emptySet());
    public static final Menu MENU3 = new Menu(START_SEQ + 8, LocalDate.of(2017, 11, 1), RESTAURANT3, Collections.emptySet());
    public static final Menu MENU4 = new Menu(START_SEQ + 9, LocalDate.of(2017, 11, 2), RESTAURANT1, Collections.emptySet());
    public static final Menu MENU5 = new Menu(START_SEQ + 10, LocalDate.of(2017, 11, 2), RESTAURANT2, Collections.emptySet());
    public static final Menu MENU6 = new Menu(START_SEQ + 11, LocalDate.of(2017, 11, 2), RESTAURANT3, Collections.emptySet());

    static {
        MENU1.getMenuList().add(MENU_DISH5);
        MENU1.getMenuList().add(MENU_DISH6);
        MENU2.getMenuList().add(MENU_DISH1);
        MENU2.getMenuList().add(MENU_DISH2);
        MENU3.getMenuList().add(MENU_DISH3);
        MENU3.getMenuList().add(MENU_DISH4);
        MENU4.getMenuList().add(MENU_DISH7);
        MENU4.getMenuList().add(MENU_DISH8);
        MENU5.getMenuList().add(MENU_DISH11);
        MENU5.getMenuList().add(MENU_DISH12);
        MENU6.getMenuList().add(MENU_DISH9);
        MENU6.getMenuList().add(MENU_DISH10);
    }

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual)
                .usingComparatorForType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualToIgnoringGivenFields(expected, "votes");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual)
                .usingElementComparatorIgnoringFields("votes")
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualTo(expected);
    }
}
