package com.planifcarbon.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(MetroController.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class MetroControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getMetroList() throws Exception {
        mvc.perform(get("/api/metro/list"))
                .andExpect(status().isOk());
    }

    @Test
    void getMetroInformation() throws Exception {
        String metroId = "10";
        mvc.perform(get("/api/metro/" + metroId))
                .andExpect(status().isOk());
    }

    @Test
    void getBestStations() throws Exception {
        mvc.perform(get("/api/metro/best-stations"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStationsCorrespondences() throws Exception {
        mvc.perform(get("/api/metro/stations-correspondence"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStations() throws Exception {
        mvc.perform(get("/api/metro/stations"))
                .andExpect(status().isOk());
    }
}
