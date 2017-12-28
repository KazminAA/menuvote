package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.MenuDish;
import com.simplevoting.menuvoting.service.DishService;
import com.simplevoting.menuvoting.service.MenuListService;
import com.simplevoting.menuvoting.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;

public class MenuListServiceImpl implements MenuListService {
    private final MenuService menuService;
    private final DishService dishService;

    @Autowired
    public MenuListServiceImpl(MenuService menuService, DishService dishService) {
        this.menuService = menuService;
        this.dishService = dishService;
    }

    @Override
    public void addDish(int menu_id, int dish_id, double price) {
        Menu menu = menuService.get(menu_id);
        MenuDish menuDish = new MenuDish(menu, dishService.get(dish_id), price);
        menu.getMenuList().add(menuDish);
        menuService.update(menu);
    }

    @Override
    public void removeDish(int menu_id, int dish_id) {
        Menu menu = menuService.get(menu_id);
        Dish dish = dishService.get(dish_id);
        for (Iterator<MenuDish> iterator = menu.getMenuList().iterator(); iterator.hasNext(); ) {
            MenuDish menuDish = iterator.next();
            if (menuDish.getDish().equals(dish)) {
                iterator.remove();
                menuDish.setMenu(null);
                menuDish.setDish(null);
            }
        }
    }
}
