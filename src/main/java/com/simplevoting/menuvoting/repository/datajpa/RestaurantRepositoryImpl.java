package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    @Autowired
    private DataJpaRestaurantRepository restaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return restaurantRepository.findById(id);
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
