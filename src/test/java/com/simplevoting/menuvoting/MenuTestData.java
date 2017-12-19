package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.*;

import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestData {
    public static final Menu MENU1 = new Menu(START_SEQ + 6, new Date(LocalDate.of(2017, 11, 01).toEpochDay()), RESTAURANT2, 1, Collections.emptySet());
    public static final Menu MENU2 = new Menu(START_SEQ + 7, new Date(LocalDate.of(2017, 11, 01).toEpochDay()), RESTAURANT1, 2, Collections.emptySet());
    public static final Menu MENU3 = new Menu(START_SEQ + 8, new Date(LocalDate.of(2017, 11, 01).toEpochDay()), RESTAURANT3, 0, Collections.emptySet());
    public static final Menu MENU4 = new Menu(START_SEQ + 9, new Date(LocalDate.of(2017, 11, 02).toEpochDay()), RESTAURANT1, 1, Collections.emptySet());
    public static final Menu MENU5 = new Menu(START_SEQ + 10, new Date(LocalDate.of(2017, 11, 02).toEpochDay()), RESTAURANT2, 1, Collections.emptySet());
    public static final Menu MENU6 = new Menu(START_SEQ + 11, new Date(LocalDate.of(2017, 11, 02).toEpochDay()), RESTAURANT3, 1, Collections.emptySet());

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual)
                .usingComparatorForType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .isEqualToIgnoringGivenFields("menuList", "votes");
    }

    public static void assertMatch(Iterable<Menu> actual, Menu... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual)
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Restaurant::getId), Restaurant.class)
                .usingElementComparatorIgnoringFields("menuList", "votes").isEqualTo(expected);
    }
}
