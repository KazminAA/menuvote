package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.MenuList;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.util.List;

public interface MenuListService {
    MenuList create(MenuList dish, int menu_id) throws NotFoundException;

    MenuList update(MenuList dish, int menu_id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<MenuList> getByMenuId(int menu_id);
}
