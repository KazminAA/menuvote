package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.repository.DishRepository;
import com.simplevoting.menuvoting.service.DishService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNew;
import static com.simplevoting.menuvoting.utils.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {
    private DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) {
        this.repository = repository;
    }

    @Override
    public Dish create(Dish dish) throws IllegalArgumentException {
        Assert.notNull(dish, "Dish must not be null.");
        checkNew(dish);
        return repository.save(dish);
    }

    @Override
    public Dish update(Dish dish) {
        Assert.notNull(dish, "Dish must not be null.");
        return repository.save(dish);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Dish get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Dish> getAll() {
        return repository.getAll();
    }
}
