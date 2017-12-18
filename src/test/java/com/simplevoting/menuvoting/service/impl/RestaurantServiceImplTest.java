package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.config.JpaConfig;
import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.simplevoting.menuvoting.RestaurantTestData.*;

@ContextConfiguration(classes = {JpaConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RestaurantServiceImplTest {
    @Autowired
    RestaurantService service;

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant(null, "New", "SomeAddress");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(service.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, newRestaurant);
    }

    @Test
    public void update() {
    }

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
    }
}