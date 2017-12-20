package com.simplevoting.menuvoting;

import com.simplevoting.menuvoting.model.AbstractBaseEntity;
import com.simplevoting.menuvoting.model.MenuList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuListTestData {
    public static final MenuList MENU_LIST1 = new MenuList(AbstractBaseEntity.START_SEQ + 12, "Свинная вырезка, 100г", 120.0);
    public static final MenuList MENU_LIST2 = new MenuList(AbstractBaseEntity.START_SEQ + 13, "Чешское светлое, 0,5л", 22.0);
    public static final MenuList MENU_LIST3 = new MenuList(AbstractBaseEntity.START_SEQ + 14, "Селедка под шубой", 55.0);
    public static final MenuList MENU_LIST4 = new MenuList(AbstractBaseEntity.START_SEQ + 15, "Молоко", 11.0);
    public static final MenuList MENU_LIST5 = new MenuList(AbstractBaseEntity.START_SEQ + 16, "Борщ классический", 45.0);
    public static final MenuList MENU_LIST6 = new MenuList(AbstractBaseEntity.START_SEQ + 17, "Макароны по флотски", 15.0);
    public static final MenuList MENU_LIST7 = new MenuList(AbstractBaseEntity.START_SEQ + 18, "Свинная вырезка, 100г", 120.95);
    public static final MenuList MENU_LIST8 = new MenuList(AbstractBaseEntity.START_SEQ + 19, "Чешское светлое, 0,5л", 18.0);
    public static final MenuList MENU_LIST9 = new MenuList(AbstractBaseEntity.START_SEQ + 20, "Борщ классический", 44.22);
    public static final MenuList MENU_LIST10 = new MenuList(AbstractBaseEntity.START_SEQ + 21, "Макароны по флотски", 15.1);
    public static final MenuList MENU_LIST11 = new MenuList(AbstractBaseEntity.START_SEQ + 22, "Пирог с черникой", 27.0);
    public static final MenuList MENU_LIST12 = new MenuList(AbstractBaseEntity.START_SEQ + 23, "Чай", 0.45);

    public static final Set<MenuList> MENU_SET1 = new HashSet<>(Arrays.asList(MENU_LIST5, MENU_LIST6));
    public static final Set<MenuList> MENU_SET2 = new HashSet<>(Arrays.asList(MENU_LIST1, MENU_LIST2));
    public static final Set<MenuList> MENU_SET3 = new HashSet<>(Arrays.asList(MENU_LIST3, MENU_LIST4));
    public static final Set<MenuList> MENU_SET4 = new HashSet<>(Arrays.asList(MENU_LIST7, MENU_LIST8));
    public static final Set<MenuList> MENU_SET5 = new HashSet<>(Arrays.asList(MENU_LIST11, MENU_LIST12));
    public static final Set<MenuList> MENU_SET6 = new HashSet<>(Arrays.asList(MENU_LIST9, MENU_LIST10));

    List<MenuList> allMenuList = Arrays.asList(MENU_LIST1, MENU_LIST2, MENU_LIST3, MENU_LIST4, MENU_LIST5, MENU_LIST6
            , MENU_LIST7, MENU_LIST8, MENU_LIST9, MENU_LIST10, MENU_LIST11, MENU_LIST12);

    public static void assertMatch(MenuList actual, MenuList expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu");
    }

    public static void assertMatch(Iterable<MenuList> actual, MenuList... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<MenuList> actual, Iterable<MenuList> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu").isEqualTo(expected);
    }
}