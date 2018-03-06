package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.service.DishService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.simplevoting.menuvoting.utils.ResponseEntityWithLocation.getResponseCreatedWithId;
import static com.simplevoting.menuvoting.utils.validation.ValidationUtil.assureIdConsistent;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    static final String REST_URL = "/rest/admin/dishes";
    private static final Logger log = getLogger(AdminDishController.class);

    private final DishService service;

    @Autowired
    public AdminDishController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Dish get(@PathVariable("id") int id) {
        log.info("get with id={}", id);
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> create(@RequestBody @Valid Dish dish) {
        log.info("create {}", dish);
        Dish created = service.create(dish);
        return getResponseCreatedWithId(created, REST_URL);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable("id") int id) throws IllegalArgumentException {
        assureIdConsistent(dish, id);
        log.info("update {} with id={}", dish, id);
        service.update(dish);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete dish with id={}", id);
        service.delete(id);
    }
}
