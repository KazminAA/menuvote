package com.simplevoting.menuvoting.web.restaurant;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/restaurant")
public class RestaurantRestController {
    private final RestaurantService service;

    @Autowired
    public RestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}")
    public Restaurant get(@PathVariable int id) {
        return service.get(id);
    }
}
