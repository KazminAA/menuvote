package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    List<Restaurant> getAll();
}
