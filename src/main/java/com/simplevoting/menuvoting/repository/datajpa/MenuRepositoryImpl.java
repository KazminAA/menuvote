package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MenuRepositoryImpl implements MenuRepository {

    @Autowired
    DataJpaMenuRepository menuRepository;

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public boolean delete(int id) {
        return menuRepository.delete(id) != 0;
    }

    @Override
    public Menu get(int id) {
        return menuRepository.findMenuById(id);
    }

    @Override
    public List<Menu> getBetween(LocalDate start, LocalDate end) {
        return menuRepository.findAllDateBetween(start, end);
    }

    @Override
    public List<Menu> getByRestaurant(int restaurant_id) {
        return menuRepository.getByRestaurantId(restaurant_id);
    }
}
