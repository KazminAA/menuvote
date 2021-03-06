package com.simplevoting.menuvoting.web.user;

import com.simplevoting.menuvoting.TestUtils;
import com.simplevoting.menuvoting.UserTestData;
import com.simplevoting.menuvoting.model.Role;
import com.simplevoting.menuvoting.model.User;
import com.simplevoting.menuvoting.service.UserService;
import com.simplevoting.menuvoting.utils.MessageUtil;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static com.simplevoting.menuvoting.TestUtils.authUser;
import static com.simplevoting.menuvoting.TestUtils.contentJsonWithIgnore;
import static com.simplevoting.menuvoting.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Autowired
    private MessageUtil messageUtil;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN1.getId())
                .with(authUser(ADMIN1)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonWithIgnore(ADMIN1, "votes"));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + ADMIN1.getEmail())
                .with(authUser(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonWithIgnore(ADMIN1, "votes"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER1.getId())
                .with(authUser(ADMIN1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN1, USER2);
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(authUser(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, updated.getPassword()))
                .with(authUser(ADMIN1)))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER1.getId()), updated);
    }

    @Test
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, expected.getPassword()))
                .with(authUser(ADMIN1)))
                .andExpect(status().isCreated());

        User returned = TestUtils.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN1, expected, USER1, USER2);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(authUser(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJsonWithIgnore(Arrays.asList(new User[]{ADMIN1, USER1, USER2}), "votes"));
    }

    @Test
    public void testCreateInvalid() throws Exception {
        User expected = new User(null, null, "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(expected))
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("VALIDATION_ERROR"));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(USER1);
        updated.setName("");
        mockMvc.perform(put(REST_URL + USER1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updated))
                .with(authUser(ADMIN1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.type").value("VALIDATION_ERROR"))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testUpdateDuplicate() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(put(REST_URL + USER1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updated, updated.getPassword()))
                .with(authUser(ADMIN1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value(messageUtil.getMessage("exception.user.duplicateEmail", new Locale("en"))))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        User expected = new User(null, "New", "user@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(expected, expected.getPassword()))
                .with(authUser(ADMIN1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value(messageUtil.getMessage("exception.user.duplicateEmail", new Locale("en"))));

    }
}