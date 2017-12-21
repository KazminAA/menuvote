package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.MenuList;
import com.simplevoting.menuvoting.repository.MenuListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuListRepositoryImpl implements MenuListRepository {
    @Autowired
    private DataJpaMenuListRepository menuListRepository;

    @Autowired
    private DataJpaMenuRepository menuRepository;

    @Override
    public MenuList save(MenuList dish, int menu_id) {
        dish.setMenu(menuRepository.getOne(menu_id));
        return menuListRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return menuListRepository.delete(id) != 0;
    }

    @Override
    public List<MenuList> getByMenuId(int menu_id) {
        return menuListRepository.findAllByMenu_Id(menu_id);
    }
}
