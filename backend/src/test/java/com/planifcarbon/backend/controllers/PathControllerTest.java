package com.planifcarbon.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(PathController.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class PathControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getBestTimePath() throws Exception {
        mvc.perform(get("/api/path/best-time-path?start='hi'&end='hello'"))
                .andExpect(status().isOk());
    }

    @Test
    void getBestDistancePath() throws Exception {
        mvc.perform(get("/api/path/best-distance-path?start='hi'&end='hello'"))
                .andExpect(status().isOk());
    }

    @Test
    void getBestTimeAndDistancePath() throws Exception{
        mvc.perform(get("/api/path/best-time-distance-path?start='hi'&end='hello'"))
                .andExpect(status().isOk());
    }
}
