package com.simplevoting.menuvoting.repository;

import com.simplevoting.menuvoting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant get(int id);

    boolean delete(int id);

    List<Restaurant> getAll();
}
