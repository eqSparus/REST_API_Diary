package ru.diary.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.diary.config.AppConfigTest;
import ru.diary.configurations.security.jwt.TokenCreator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfigTest.class)
@WebAppConfiguration
@Sql(value = {"/create_user_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_user_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EmailControllerTest {


    private WebApplicationContext wac;
    private TokenCreator creator;
    private MockMvc mvc;


    @Autowired
    public EmailControllerTest(WebApplicationContext wac, TokenCreator creator) {
        this.wac = wac;
        this.creator = creator;
    }


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void resetPassword() {
        mvc.perform();
    }
}
