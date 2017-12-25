package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.util.List;

public interface DishService {
    Dish create(Dish dish) throws IllegalArgumentException;

    Dish update(Dish dish);

    void delete(int id) throws NotFoundException;

    Dish get(int id) throws NotFoundException;

    List<Dish> getAll();
}
