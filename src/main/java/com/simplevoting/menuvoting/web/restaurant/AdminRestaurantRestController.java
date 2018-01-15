package com.simplevoting.menuvoting.web.restaurant;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.simplevoting.menuvoting.utils.ResponseEntityWithLocation.getResponseCreatedWithId;
import static com.simplevoting.menuvoting.utils.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL)
public class AdminRestaurantRestController {
    static final String REST_URL = "rest/admin/restaurant";

    private final RestaurantService service;

    @Autowired
    public AdminRestaurantRestController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@RequestBody @Valid Restaurant restaurant) {
        Restaurant created = service.create(restaurant);
        return getResponseCreatedWithId(created, REST_URL);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) throws IllegalArgumentException {
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
