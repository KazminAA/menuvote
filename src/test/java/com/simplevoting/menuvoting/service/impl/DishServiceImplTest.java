package com.simplevoting.menuvoting.service.impl;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.service.DishService;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.simplevoting.menuvoting.DishTestData.*;

public class DishServiceImplTest extends AbstractWebAppTest {
    @Autowired
    private DishService service;

    @Test
    public void create() {
        Dish newDish = new Dish(null, "Яблоки", "сладкие, сочные");
        Dish created = service.create(newDish);
        assertMatch(service.getAll(), DISH5, DISH6, DISH4, DISH7, DISH1, DISH3, DISH8, DISH2, created);
    }

    @Test
    public void createWithIdNotNull() {
        thrown.expect(IllegalArgumentException.class);
        Dish created = new Dish(2, "", "");
        service.create(created);
    }

    @Test
    public void update() {
        Dish updated = new Dish(DISH1);
        updated.setName("newDish");
        updated.setDescription("newDescr");
        service.update(updated);
        assertMatch(service.getAll(), updated, DISH5, DISH6, DISH4, DISH7, DISH3, DISH8, DISH2);
    }

    @Test
    public void delete() {
        service.delete(DISH1.getId());
        assertMatch(service.getAll(), DISH5, DISH6, DISH4, DISH7, DISH3, DISH8, DISH2);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        service.delete(2);
    }

    @Test
    public void get() {
        assertMatch(service.get(DISH4.getId()), DISH4);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NotFoundException.class);
        service.get(2);
    }

    @Test
    public void getAll() {
        List<Dish> dishes = service.getAll();
        assertMatch(dishes, DISH5, DISH6, DISH4, DISH7, DISH1, DISH3, DISH8, DISH2);
    }

    public void testValidation() {
        validateRootCause(() -> service.create(new Dish(null, "", "")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "Im", "")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, null, "")), ConstraintViolationException.class);
    }
}