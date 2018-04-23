package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.repository.RestaurantRepository;
import com.simplevoting.menuvoting.service.RestaurantService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNew;
import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.checkNotFoundWithId;

@Service
@CacheConfig(cacheNames = "restaurants")
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(allEntries = true)
    @Override
    public Restaurant create(Restaurant restaurant) throws IllegalArgumentException {
        Assert.notNull(restaurant, "Restaurant mast not be null.");
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    @CacheEvict(allEntries = true)
    @Override
    public Restaurant update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "Restaurant mast not be null.");
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Cacheable
    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }
}
