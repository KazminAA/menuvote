package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.simplevoting.menuvoting.utils.ResponseEntityWithLocation.getResponseCreatedWithId;
import static com.simplevoting.menuvoting.utils.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    static final String REST_URL = "rest/admin/dish";

    private final DishService service;

    @Autowired
    public AdminDishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@RequestBody @Valid Dish dish) {
        Dish created = service.create(dish);
        return getResponseCreatedWithId(created, REST_URL);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("id") int id) throws IllegalArgumentException {
        assureIdConsistent(dish, id);
        service.update(dish);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
