package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RestaurantRepositoryImpl implements RestaurantRepository {
    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findOne(id);
    }

    @Override
    public boolean delete(int id) {
        return restaurantRepository.delete(id) != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
