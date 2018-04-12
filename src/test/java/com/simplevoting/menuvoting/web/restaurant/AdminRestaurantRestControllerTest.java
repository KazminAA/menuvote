package com.simplevoting.menuvoting.web.restaurant;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.TestUtils.readFromJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = AdminRestaurantRestController.REST_URL + "/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testCreate() throws Exception {
        Restaurant created = new Restaurant("Created", "st.Test, 1");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(created)));
        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(created, returned);
        assertMatch(restaurantService.getAll(), RESTAURANT1, RESTAURANT2, RESTAURANT3, created);
    }

    @Test
    public void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT2);
        updated.setName("Updated");
        updated.setAddress("st.Street, 555");
        mockMvc.perform(put(REST_URL + RESTAURANT2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updated)));
        assertMatch(restaurantService.getAll(), RESTAURANT1, updated, RESTAURANT3);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT2.getId()));
        assertMatch(restaurantService.getAll(), RESTAURANT1, RESTAURANT3);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "15"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("DATA_NOT_FOUND"));
    }

    @Test
    public void testCreateInvalidName() throws Exception {
        Restaurant invalid = new Restaurant(null, "st.Street, 12");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(invalid)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("VALIDATION_ERROR"));
    }
}