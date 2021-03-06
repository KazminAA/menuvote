package com.simplevoting.menuvoting.web.restaurant;

import com.simplevoting.menuvoting.model.Restaurant;
import com.simplevoting.menuvoting.service.RestaurantService;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.simplevoting.menuvoting.RestaurantTestData.*;
import static com.simplevoting.menuvoting.TestUtils.authUser;
import static com.simplevoting.menuvoting.TestUtils.readFromJson;
import static com.simplevoting.menuvoting.UserTestData.ADMIN1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = AdminRestaurantRestController.REST_URL + "/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testCreate() throws Exception {
        Restaurant created = new Restaurant("Created", "st.Test, 1");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(created))
                .with(authUser(ADMIN1)));
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
                .content(JsonUtils.wrightValue(updated))
                .with(authUser(ADMIN1)));
        assertMatch(restaurantService.getAll(), RESTAURANT1, updated, RESTAURANT3);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT2.getId())
                .with(authUser(ADMIN1)));
        assertMatch(restaurantService.getAll(), RESTAURANT1, RESTAURANT3);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "15")
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("DATA_NOT_FOUND"));
    }

    @Test
    public void testCreateInvalidName() throws Exception {
        Restaurant invalid = new Restaurant(null, "st.Street, 12");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(invalid))
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("VALIDATION_ERROR"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testDuplicate() throws Exception {
        Restaurant duplicate = new Restaurant(RESTAURANT1.getName(), "st.SomeOne, 1");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(duplicate))
                .with(authUser(ADMIN1)))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value("Restaurant with this name already exists"));
    }
}