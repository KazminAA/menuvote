package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.*;

import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {
    public static final Menu MENU1 = new Menu(START_SEQ + 6, LocalDate.of(2017, 11, 01), RESTAURANT2, 1, Collections.emptySet());
    public static final Menu MENU2 = new Menu(START_SEQ + 7, LocalDate.of(2017, 11, 01), RESTAURANT1, 2, Collections.emptySet());
    public static final Menu MENU3 = new Menu(START_SEQ + 8, LocalDate.of(2017, 11, 01), RESTAURANT3, 0, Collections.emptySet());
    public static final Menu MENU4 = new Menu(START_SEQ + 9, LocalDate.of(2017, 11, 02), RESTAURANT1, 1, Collections.emptySet());
    public static final Menu MENU5 = new Menu(START_SEQ + 10, LocalDate.of(2017, 11, 02), RESTAURANT2, 1, Collections.emptySet());
    public static final Menu MENU6 = new Menu(START_SEQ + 11, LocalDate.of(2017, 11, 02), RESTAURANT3, 1, Collections.emptySet());

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual)
                .usingComparatorForType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualToIgnoringGivenFields(expected, "menuList", "votes");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual)
                .usingElementComparatorIgnoringFields("menuList", "votes")
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualTo(expected);
    }
}
