package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Restaurant;

import java.util.Arrays;

import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final Restaurant RESTAURANT1 = new Restaurant(START_SEQ + 3, "Арчибальд", "ул.Большая, 21");
    public static final Restaurant RESTAURANT2 = new Restaurant(START_SEQ + 4, "YellowSubmarine", "ул.Никиты-Кожемяки, 33");
    public static final Restaurant RESTAURANT3 = new Restaurant(START_SEQ + 5, "Словянка", "ул.Неокрашенная, 2");

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
