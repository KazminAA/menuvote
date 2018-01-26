package com.simplevoting.menuvoting.web.menu;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.MenuDish;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.to.DishTo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.simplevoting.menuvoting.utils.ResponseEntityWithLocation.getResponseCreatedWithId;
import static com.simplevoting.menuvoting.utils.ValidationUtil.assureIdConsistent;
import static com.simplevoting.menuvoting.web.menu.AdminMenuRestController.REST_URL;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = REST_URL)
public class AdminMenuRestController {
    static final String REST_URL = "/rest/admin/menu";
    private static final Logger log = getLogger(AdminMenuRestController.class);

    private final MenuService service;

    @Autowired
    public AdminMenuRestController(MenuService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> create(@RequestBody @Valid Menu menu) {
        log.info("create {}", menu);
        Menu created = service.create(menu);
        return getResponseCreatedWithId(created, REST_URL);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid Menu menu, @PathVariable("id") int id) throws IllegalArgumentException {
        assureIdConsistent(menu, id);
        log.info("update {} with id={}", menu, id);
        service.update(menu);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> addDishes(@RequestBody @Valid DishTo[] dishes, @PathVariable("id") int id) {
        Menu menu = service.get(id);
        Arrays.stream(dishes).forEach(dishTo -> {
            MenuDish newMD = new MenuDish(menu, dishTo.getDish(), dishTo.getPrice());
            menu.getDishes().add(newMD);
        });
        service.update(menu);
        return getResponseCreatedWithId(menu, REST_URL);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete dish with id={}", id);
        service.delete(id);
    }

}
