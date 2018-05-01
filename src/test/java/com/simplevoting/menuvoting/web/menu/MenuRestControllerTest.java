package com.simplevoting.menuvoting.web.menu;

import com.simplevoting.menuvoting.model.Menu;
import com.simplevoting.menuvoting.service.MenuService;
import com.simplevoting.menuvoting.web.AbstractControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.simplevoting.menuvoting.MenuTestData.*;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT1;
import static com.simplevoting.menuvoting.RestaurantTestData.RESTAURANT2;
import static com.simplevoting.menuvoting.TestUtils.authUser;
import static com.simplevoting.menuvoting.TestUtils.contentJson;
import static com.simplevoting.menuvoting.UserTestData.USER1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MenuRestController.REST_URL + "/";

    @Autowired
    private MenuService menuService;

    @Test
    public void testGetTodayAll() throws Exception {
        List<Menu> todayMenu = Arrays.asList(new Menu[]{
                new Menu(LocalDate.now(), RESTAURANT1),
                new Menu(LocalDate.now(), RESTAURANT2)
        });
        todayMenu.forEach(menu -> menuService.create(menu));
        mockMvc.perform(get(REST_URL)
                .with(authUser(USER1)))
                .andExpect(status().isOk())
                .andExpect(contentJson(todayMenu));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "all")
                .with(authUser(USER1)))
                .andExpect(status().isOk())
                .andExpect(contentJson(Arrays.asList(new Menu[]{MENU5, MENU4, MENU6, MENU1, MENU2, MENU3})));
    }

    @Test
    public void GetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2017-11-01&endDate=2017-11-01")
                .with(authUser(USER1)))
                .andExpect(status().isOk())
                .andExpect(contentJson(Arrays.asList(new Menu[]{MENU1, MENU2, MENU3})));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU5.getId())
                .with(authUser(USER1)))
                .andExpect(status().isOk())
                .andExpect(contentJson(MENU5));
    }
}