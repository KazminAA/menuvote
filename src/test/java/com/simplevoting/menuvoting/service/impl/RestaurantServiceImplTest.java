package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.simplevoting.menuvoting.RestaurantTestData.*;

public class RestaurantServiceImplTest extends AbstractWebAppTest {
    @Autowired
    RestaurantService service;

    @Test
    public void create() {
        Restaurant newRestaurant = new Restaurant(null, "New", "str.Brandon, 24");
        Restaurant created = service.create(newRestaurant);
        newRestaurant.setId(created.getId());
        assertMatch(service.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, created);
    }

    @Test
    public void update() {
        Restaurant updated = new Restaurant(RESTAURANT2);
        updated.setName("Updated");
        updated.setAddress("str.Updated, 1a");
        service.update(updated);
        assertMatch(service.get(RESTAURANT2.getId()), updated);
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT3.getId());
        assertMatch(restaurant, RESTAURANT3);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        Restaurant restaurant = service.get(1);
    }

    @Test
    public void delete() {
        service.delete(RESTAURANT2.getId());
        assertMatch(service.getAll(), RESTAURANT1, RESTAURANT3);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        service.delete(1);
    }

    @Test
    public void getAll() {
        List<Restaurant> result = service.getAll();
        assertMatch(result, RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> service.create(new Restaurant(null, "New", "SomeAddr")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "Ne", "str.Brandon, 24")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "", "str.Brandon, 24")), ConstraintViolationException.class);
    }
}