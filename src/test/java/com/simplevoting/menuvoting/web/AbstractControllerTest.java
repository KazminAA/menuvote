package com.simplevoting.menuvoting.web;

import com.simplevoting.menuvoting.MenuTestData;
import com.simplevoting.menuvoting.UserTestData;
import com.simplevoting.menuvoting.config.WebConfig;
import com.simplevoting.menuvoting.service.impl.AbstractWebAppTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

abstract public class AbstractControllerTest extends AbstractWebAppTest {

    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private Filter springSecurityFilterChain;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
        MenuTestData.fillSets();
        UserTestData.fillSets();
    }

    @Before
    public void setUp() {
        cacheManager.getCache("users").clear();
        cacheManager.getCache("dishes").clear();
        cacheManager.getCache("restaurants").clear();
    }
}
