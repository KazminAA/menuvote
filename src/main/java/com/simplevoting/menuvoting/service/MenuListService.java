package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.MenuList;

import java.util.List;

public interface MenuListService {
    MenuList create(MenuList dish, int menu_id);

    MenuList update(MenuList dish, int menu_id);

    void delete(int id);

    List<MenuList> getByMenuId(int menu_id);
}
