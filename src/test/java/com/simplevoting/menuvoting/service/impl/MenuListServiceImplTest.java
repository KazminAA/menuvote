package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.MenuList;
import com.simplevoting.menuvoting.service.MenuListService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static com.simplevoting.menuvoting.MenuListTestData.*;
import static com.simplevoting.menuvoting.MenuTestData.MENU3;
import static com.simplevoting.menuvoting.MenuTestData.MENU4;

public class MenuListServiceImplTest extends AbstractServiceTest {
    @Autowired
    private MenuListService service;

    @Test
    public void create() {
        MenuList newMenuList = new MenuList(null, "Сосиска по камышански", 54.0);
        MenuList created = service.create(newMenuList, MENU3.getId());
        assertMatch(service.getByMenuId(MENU3.getId()), MENU_LIST3, MENU_LIST4, created);
    }

    @Test
    public void createWithNonExistentMenuId() {
        thrown.expect(DataIntegrityViolationException.class);
        MenuList newMenuList = new MenuList(null, "Сосиска по камышански", 54.0);
        service.create(newMenuList, 1);
    }

    @Test
    public void update() {
        MenuList updated = new MenuList(MENU_LIST3);
        updated.setDish("UPDATED_DISH");
        updated.setPrice(500.00);
        service.update(updated, MENU3.getId());
        assertMatch(service.getByMenuId(MENU3.getId()), updated, MENU_LIST4);
    }

    @Test
    public void delete() {
        service.delete(MENU_LIST3.getId());
        assertMatch(service.getByMenuId(MENU3.getId()), MENU_LIST4);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        service.delete(5);
    }

    @Test
    public void getByMenuId() {
        List<MenuList> menuList = service.getByMenuId(MENU4.getId());
        assertMatch(menuList, MENU_LIST7, MENU_LIST8);
    }
}