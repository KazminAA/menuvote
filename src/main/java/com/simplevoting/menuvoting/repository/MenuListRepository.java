package com.simplevoting.menuvoting.repository;

import com.simplevoting.menuvoting.model.MenuList;

import java.util.List;

public interface MenuListRepository {
    MenuList save(MenuList dish, int menu_id);

    boolean delete(int id);

    List<MenuList> getByMenuId(int menu_id);
}
