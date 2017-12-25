package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishRepositoryImpl implements DishRepository {
    private static final Sort DISH_NAME_SORT = new Sort(Sort.Direction.ASC, "name");
    private DataJpaDishRepository dataJpaDishRepository;

    @Autowired
    public DishRepositoryImpl(DataJpaDishRepository dataJpaDishRepository) {
        this.dataJpaDishRepository = dataJpaDishRepository;
    }

    @Override
    public Dish save(Dish dish) {
        return dataJpaDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return dataJpaDishRepository.delete(id) != 0;
    }

    @Override
    public Dish get(int id) {
        return dataJpaDishRepository.findById(id).orElse(null);
    }

    @Override
    public List<Dish> getAll() {
        return dataJpaDishRepository.findAll(DISH_NAME_SORT);
    }
}
