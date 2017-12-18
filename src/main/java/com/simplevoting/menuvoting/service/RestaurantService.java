package com.simplevoting.menuvoting.service;

import com.simplevoting.menuvoting.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    Restaurant update(Restaurant restaurant);

    Restaurant get(int id);

    void delete(int id);

    List<Restaurant> getAll();
}
