package com.simplevoting.menuvoting.web.menu;

import com.simplevoting.menuvoting.TestUtils;
import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.model.MenuDish;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.simplevoting.menuvoting.DishTestData.DISH2;
import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminMenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminMenuRestController.REST_URL + "/";

    @Autowired
    private MenuService menuService;

    @Test
    public void testCreate() throws Exception {
        Menu created = new Menu(LocalDate.now(), RESTAURANT2);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(created)));
        Menu returned = TestUtils.readFromJson(action, Menu.class);
        created.setId(returned.getId());
        assertMatch(created, returned);
        assertMatch(menuService.getAll(), created, MENU5, MENU4, MENU6, MENU1, MENU2, MENU3);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testDuplicate() throws Exception {
        Menu duplicate = new Menu(MENU6.getDate(), MENU6.getRestaurant());
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(duplicate)))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value("At this date the menu for the restaurant already exists"));
    }

    @Test
    public void testUpdate() throws Exception {
        Menu updated = new Menu(MENU6);
        updated.setDate(LocalDate.of(2018, 1, 1));
        MenuDish dish = new MenuDish(updated, DISH2, 121.20);
        updated.getDishes().add(dish);
        mockMvc.perform(put(REST_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updated)));
        assertMatch(menuService.getAll(), updated, MENU5, MENU4, MENU1, MENU2, MENU3);
    }

/*    @Test
    public void testAddDishes() {
    }*/

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU4.getId()));
        assertMatch(menuService.getAll(), MENU5, MENU6, MENU1, MENU2, MENU3);
    }
}