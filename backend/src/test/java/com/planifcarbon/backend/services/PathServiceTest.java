package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.DjikstraSearchResultDTO;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.dtos.StationCorrespondence;
import com.planifcarbon.backend.model.MetroMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {PathService.class, MetroMap.class})
@TestPropertySource(locations = "classpath:application-tests.properties")
class PathServiceTest {

    @Autowired
    private PathService service;

    @ParameterizedTest
    @CsvSource({"Bercy, Gare du Nord, 53100, TIME, METRO", "Bercy, Gare du Nord, 53100, TIME, METRO_FOOT", "Bercy, Gare du Nord, 53100, TIME, FOOT"})
    public void testGetBestPath(String start, String end, int time, String method, String transportation) {
        List<DjikstraSearchResultDTO> result = service.getBestPath(start, end, time, method, transportation);
        assertNotNull(result);
    }

    @Test
    public void testGetBestPathWithPersonalizedNodes() {
        List<DjikstraSearchResultDTO> resultWithStartNode = service.getBestPath("(48.84014763512746, 2.3791909087742877)", "Bercy", 53100, "TIME", "METRO");
        assertNotNull(resultWithStartNode);

        List<DjikstraSearchResultDTO> resultWithEndNode = service.getBestPath("Gare du Nord", "(48.84014763512746, 2.3791909087742877)", 53100, "TIME", "METRO");
        assertNotNull(resultWithEndNode);

        List<DjikstraSearchResultDTO> resultWithBothNodes = service.getBestPath("(48.87970181695894, 2.3572164888604563)", "(48.84014763512746, 2.3791909087742877)", 53100, "TIME", "METRO");
        assertNotNull(resultWithEndNode);
    }
}
