package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.MenuDish;

import java.util.Arrays;
import java.util.Comparator;

import static com.simplevoting.menuvoting.DishTestData.*;
import static com.simplevoting.menuvoting.MenuTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuDishTestData {
    public static final MenuDish MENU_DISH1 = new MenuDish(MENU2, DISH1, 120.00);
    public static final MenuDish MENU_DISH2 = new MenuDish(MENU2, DISH2, 22.00);
    public static final MenuDish MENU_DISH3 = new MenuDish(MENU3, DISH3, 55.00);
    public static final MenuDish MENU_DISH4 = new MenuDish(MENU3, DISH4, 11.00);
    public static final MenuDish MENU_DISH5 = new MenuDish(MENU1, DISH5, 45.00);
    public static final MenuDish MENU_DISH6 = new MenuDish(MENU1, DISH6, 15.00);
    public static final MenuDish MENU_DISH7 = new MenuDish(MENU4, DISH1, 120.95);
    public static final MenuDish MENU_DISH8 = new MenuDish(MENU4, DISH2, 18.00);
    public static final MenuDish MENU_DISH9 = new MenuDish(MENU6, DISH5, 44.22);
    public static final MenuDish MENU_DISH10 = new MenuDish(MENU6, DISH6, 15.10);
    public static final MenuDish MENU_DISH11 = new MenuDish(MENU5, DISH7, 27.00);
    public static final MenuDish MENU_DISH12 = new MenuDish(MENU5, DISH8, 0.45);

    public static void assertMatch(MenuDish actual, MenuDish expected) {
        assertThat(actual)
                .usingComparatorForType(Comparator.comparing(Menu::getId), Menu.class)
                .usingComparatorForType(Comparator.comparing(Dish::getId), Dish.class)
                .isEqualToComparingFieldByFieldRecursively(expected);
    }

    public static void assertMatch(Iterable<MenuDish> actual, MenuDish... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<MenuDish> actual, Iterable<MenuDish> expected) {
        assertThat(actual)
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Menu::getId), Menu.class)
                .usingComparatorForElementFieldsWithType(Comparator.comparing(Dish::getId), Dish.class)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }
}
