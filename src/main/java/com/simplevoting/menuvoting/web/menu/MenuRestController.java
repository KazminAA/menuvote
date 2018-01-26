package com.simplevoting.menuvoting.web.menu;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.service.MenuService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.simplevoting.menuvoting.utils.DateTimeUtils.MAX_DATE;
import static com.simplevoting.menuvoting.utils.DateTimeUtils.MIN_DATE;
import static com.simplevoting.menuvoting.web.menu.MenuRestController.REST_URL;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    static final String REST_URL = "/rest/menu";
    private static final Logger log = getLogger(MenuRestController.class);
    private final MenuService service;

    @Autowired
    public MenuRestController(MenuService service) {
        this.service = service;
    }

    @GetMapping
    public List<Menu> getTodayAll() {
        log.info("getTodayAll");
        return service.getByDate(LocalDate.now());
    }

    @GetMapping(value = "/all")
    public List<Menu> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping(value = "/filter")
    public List<Menu> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                 @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        LocalDate start = startDate != null ? startDate : MIN_DATE;
        LocalDate end = endDate != null ? endDate : MAX_DATE;
        log.info("getBetween {} and {}", start.toString(), end.toString());
        return service.getBetween(start, end);
    }

    @GetMapping(value = "/{id}")
    public Menu get(@PathVariable("id") int id) {
        log.info("get with id={}", id);
        return service.get(id);
    }
}
