package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.model.Dish;
import com.simplevoting.menuvoting.service.DishService;
import com.simplevoting.menuvoting.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.simplevoting.menuvoting.DishTestData.*;
import static com.simplevoting.menuvoting.TestUtils.contentJson;
import static com.simplevoting.menuvoting.TestUtils.readFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminDishController.REST_URL + '/';

    @Autowired
    private DishService dishService;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH_LIST));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH4.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH4));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "15"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("DATA_NOT_FOUND"));
    }

    @Test
    public void testCreate() throws Exception {
        Dish created = new Dish("Created dish", "just for test");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(created)));
        Dish returned = readFromJson(action, Dish.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        assertMatch(dishService.getAll(), created, DISH5, DISH6, DISH4, DISH7, DISH1, DISH3, DISH8, DISH2);
    }

    @Test
    public void testUpdate() throws Exception {
        Dish updated = new Dish(DISH4);
        updated.setName("Updated dish");
        updated.setDescription("just for test");
        mockMvc.perform(put(REST_URL + DISH4.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updated)));
        assertMatch(dishService.getAll(), updated, DISH5, DISH6, DISH7, DISH1, DISH3, DISH8, DISH2);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1.getId()));
        assertMatch(dishService.getAll(), DISH5, DISH6, DISH4, DISH7, DISH3, DISH8, DISH2);
    }
}