package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.*;

import static com.simplevoting.menuvoting.MenuDishTestData.*;
import static com.simplevoting.menuvoting.MenuDishTestData.MENU_DISH2;
import static com.simplevoting.menuvoting.MenuDishTestData.MENU_DISH5;
import static com.simplevoting.menuvoting.MenuDishTestData.MENU_DISH6;
import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {
    public static final Menu MENU1 = new Menu(START_SEQ + 6, LocalDate.of(2017, 11, 1), RESTAURANT2, new HashSet<>(Arrays.asList(MENU_DISH5, MENU_DISH6)));
    public static final Menu MENU2 = new Menu(START_SEQ + 7, LocalDate.of(2017, 11, 1), RESTAURANT1, new HashSet<>(Arrays.asList(MENU_DISH1, MENU_DISH2)));
    public static final Menu MENU3 = new Menu(START_SEQ + 8, LocalDate.of(2017, 11, 1), RESTAURANT3, new HashSet<>(Arrays.asList(MENU_DISH3, MENU_DISH4)));
    public static final Menu MENU4 = new Menu(START_SEQ + 9, LocalDate.of(2017, 11, 2), RESTAURANT1, new HashSet<>(Arrays.asList(MENU_DISH7, MENU_DISH8)));
    public static final Menu MENU5 = new Menu(START_SEQ + 10, LocalDate.of(2017, 11, 2), RESTAURANT2, new HashSet<>(Arrays.asList(MENU_DISH11, MENU_DISH12)));
    public static final Menu MENU6 = new Menu(START_SEQ + 11, LocalDate.of(2017, 11, 2), RESTAURANT3, new HashSet<>(Arrays.asList(MENU_DISH9, MENU_DISH10)));

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual)
                .usingComparatorForType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual)
                .usingElementComparatorIgnoringFields("dishes", "votes")
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualTo(expected);
    }
}
