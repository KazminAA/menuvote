package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.MenuList;
import com.simplevoting.menuvoting.repository.MenuListRepository;
import com.simplevoting.menuvoting.service.MenuListService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNew;
import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuListServiceImpl implements MenuListService {
    private final MenuListRepository repository;

    @Autowired
    public MenuListServiceImpl(MenuListRepository repository) {
        this.repository = repository;
    }

    //TODO saved new dish must return to controller where it will be added to certain menu
    @Override
    public MenuList create(MenuList dish, int menu_id) throws NotFoundException {
        Assert.notNull(dish, "Dish must be null.");
        checkNew(dish);
        return checkNotFoundWithId(repository.save(dish, menu_id), menu_id);
    }

    //TODO saved dish must return to controller where it will be update certain menu
    @Override
    public MenuList update(MenuList dish, int menu_id) throws NotFoundException {
        Assert.notNull(dish, "Dish must be null.");
        return checkNotFoundWithId(repository.save(dish, menu_id), menu_id);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public List<MenuList> getByMenuId(int menu_id) {
        return repository.getByMenuId(menu_id);
    }
}
