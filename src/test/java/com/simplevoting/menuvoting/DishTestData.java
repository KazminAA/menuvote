package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Dish;

import java.util.Arrays;

import static com.simplevoting.menuvoting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final Dish DISH1 = new Dish(START_SEQ + 12, "Свинная вырезка", "готовится на мангале");
    public static final Dish DISH2 = new Dish(START_SEQ + 13, "Чешское светлое", "пиво по классическому рецепту");
    public static final Dish DISH3 = new Dish(START_SEQ + 14, "Селедка под шубой", "пролетарский салат");
    public static final Dish DISH4 = new Dish(START_SEQ + 15, "Молоко", "коровье, 33% жирность");
    public static final Dish DISH5 = new Dish(START_SEQ + 16, "Борщ классический", "просто борщ");
    public static final Dish DISH6 = new Dish(START_SEQ + 17, "Макароны по флотски", "макароны с мясом");
    public static final Dish DISH7 = new Dish(START_SEQ + 18, "Пирог с черникой", "долго не черствеет");
    public static final Dish DISH8 = new Dish(START_SEQ + 19, "Чай", "сорт Принцесса Дури");

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
