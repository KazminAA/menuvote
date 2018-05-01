package com.simplevoting.menuvoting.web.user;

import com.simplevoting.menuvoting.AuthorizedUser;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.to.UserTo;
import com.simplevoting.menuvoting.utils.UserUtil;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.simplevoting.menuvoting.TestUtils.authUser;
import static com.simplevoting.menuvoting.TestUtils.contentJson;
import static com.simplevoting.menuvoting.TestUtils.contentJsonWithIgnore;
import static com.simplevoting.menuvoting.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL + "/";

    @Autowired
    private UserService userService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(authUser(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER1));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(authUser(USER1)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN1, USER2);
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(USER1.getId(), "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updatedTo))
                .with(authUser(USER1)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER1), updatedTo));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(USER1.getId(), null, "password", null);

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(authUser(USER1))
                .content(JsonUtils.wrightValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("VALIDATION_ERROR"))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", "admin@gmail.com", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updatedTo))
                .with(authUser(USER1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value("User with this email already exists"))
                .andDo(print());
    }
}