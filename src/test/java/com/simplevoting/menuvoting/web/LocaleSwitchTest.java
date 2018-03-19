package com.simplevoting.menuvoting.web;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocaleSwitchTest extends AbstractControllerTest {
    @Test
    public void notFoundExceptionMessageTest() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept-Language", "en");
        mockMvc.perform(get("/rest/menu/10015").headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.details[0]").value("Not found entity with id=10015"));
    }
}
