package com.planifcarbon.backend.controllers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(PathController.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class PathControllerTest {

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @CsvSource({"Bercy, Gare du Nord, 53100, TIME, METRO", "Bercy, Gare du Nord, 79200, TIME, METRO_FOOT"})
    void getBestPath(String startNode, String endNode, int time, String method, String transportation) throws Exception {
        String query = getQuery(startNode, endNode, time, method, transportation);
        MvcResult result = mvc.perform(get(query)).andExpect(status().isOk()).andReturn();
        assertNotNull(result);
        MockHttpServletResponse response = result.getResponse();
        assertEquals("application/json", response.getContentType());
    }

    private String getQuery(String startNode, String endNode, int time, String method, String transportation) {
        return String.format("/api/path/best-path?start=%s&end=%s&time=%d&method=%s&transportation=%s",startNode, endNode, time, method, transportation);
    }
}
