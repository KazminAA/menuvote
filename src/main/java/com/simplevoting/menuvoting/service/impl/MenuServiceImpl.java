package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.repository.MenuRepository;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNew;
import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public Menu create(Menu menu) throws IllegalArgumentException {
        Assert.notNull(menu, "Menu mast not be null.");
        checkNew(menu);
        return repository.save(menu);
    }

    @Override
    public Menu update(Menu menu) throws NotFoundException {
        Assert.notNull(menu, "Menu mast not be null.");
        return checkNotFoundWithId(repository.save(menu), menu.getId());
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Menu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Menu> getBetween(LocalDate start, LocalDate end) {
        return repository.getBetween(start, end);
    }

    @Override
    public List<Menu> getBetweenWithVotes(LocalDate start, LocalDate end) {
        return repository.getBetweenWithVotes(start, end);
    }
}
