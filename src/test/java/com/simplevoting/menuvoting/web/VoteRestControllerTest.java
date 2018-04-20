package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.AuthorizedUser;
import com.simplevoting.menuvoting.TestUtils;
import com.simplevoting.menuvoting.model.Vote;
import com.simplevoting.menuvoting.service.impl.VoteServiceImpl;
import com.simplevoting.menuvoting.to.VoteTo;
import com.simplevoting.menuvoting.utils.VoteUtils;
import com.simplevoting.menuvoting.utils.json.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.simplevoting.menuvoting.MenuTestData.MENU1;
import static com.simplevoting.menuvoting.MenuTestData.MENU2;
import static com.simplevoting.menuvoting.MenuTestData.MENU6;
import static com.simplevoting.menuvoting.UserTestData.USER1;
import static com.simplevoting.menuvoting.VoteTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteRestController.REST_URL + "/";

    @Autowired
    private VoteServiceImpl voteService;
    @Autowired
    private VoteUtils voteUtils;

    @Test
    public void testCreate() throws Exception {
        ResultActions resultAction = mockMvc.perform(post(REST_URL + "?menu_id=" + MENU6.getId()));
        Vote created = voteUtils.getFromTo(TestUtils.readFromJson(resultAction, VoteTo.class));
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateDuplicate() throws Exception {
        mockMvc.perform(post(REST_URL + "?=menu_id=" + MENU2.getId()))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("DATA_ERROR"))
                .andExpect(jsonPath("$.details").value("Already voted on date of this menu"));
    }

    @Test
    public void testUpdate() throws Exception {
        Vote created = voteService.create(getNewTomorrowVote(), USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
        created.setMenu(MENU1);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(created)));
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void updateOnClosedPeriodTest() throws Exception {
        Vote updated = new Vote(VOTE1);
        updated.setMenu(MENU1);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.wrightValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("EDIT_CLOSED_PERIOD_ERROR"))
                .andExpect(jsonPath("$.details").value("Date 2017-11-01 closed for voting"));
    }

    @Test
    public void testDelete() throws Exception {
        Vote created = voteService.create(getNewTomorrowVote(), USER1.getId());
        assertMatch(voteService.getAll(), created, VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
        mockMvc.perform(delete(REST_URL + created.getDate().toString()));
        assertMatch(voteService.getAll(), VOTE4, VOTE5, VOTE1, VOTE2, VOTE3);
    }

    @Test
    public void testDeleteOnClosedPeriod() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE1.getDate().toString()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("EDIT_CLOSED_PERIOD_ERROR"))
                .andExpect(jsonPath("$.details").value("Date 2017-11-01 closed for voting"));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + getNewTomorrowVote().getDate().toString()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("DATA_NOT_FOUND"))
                .andExpect(jsonPath("$.details").value("Not found entity with id=" + AuthorizedUser.id()));
    }
}