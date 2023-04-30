package com.planifcarbon.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Metro controller used to test metro functionalities.
 */
@AutoConfigureMockMvc
@WebMvcTest(MetroController.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class MetroControllerTest {

    /**
     * This class contains unit tests for the MetroController class.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Tests the /api/metro/list endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getMetroList() throws Exception {
        mvc.perform(get("/api/metro/list"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the /api/metro/{metroId} endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getMetroInformation() throws Exception {
        String metroId = "10";
        mvc.perform(get("/api/metro/" + metroId))
                .andExpect(status().isOk());
    }

    /**
     * Tests the /api/metro/best-stations endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getBestStations() throws Exception {
        mvc.perform(get("/api/metro/best-stations"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the /api/metro/stations-correspondence endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getAllStationsCorrespondences() throws Exception {
        mvc.perform(get("/api/metro/stations-correspondence"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the /api/metro/stations endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getAllStations() throws Exception {
        mvc.perform(get("/api/metro/stations"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the /api/metro/station-schedules endpoint.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void getLineSchedulesForStation() throws Exception {
        mvc.perform(get("/api/metro/station-schedules?station=Bercy&line=6"))
                .andExpect(status().isOk());
    }
}
