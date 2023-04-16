package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.StationCorrespondence;
import com.planifcarbon.backend.dtos.StationDTO;
import com.planifcarbon.backend.model.MetroMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {MetroService.class, MetroMap.class})
@TestPropertySource(locations = "classpath:application-tests.properties")
class MetroServiceTest {

    @Autowired
    private MetroService service;

    @Test
    void getMetros() {
        int nbLines = 16;
        assertEquals(nbLines, service.getMetros().size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "5"})
    void testGetMetroByName(String metroName) {
        assertNotNull(service.getMetroByName(metroName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"17"})
    void testGetMetroByNameNotExists(String metroName) {
        assertEquals(0, service.getMetroByName(metroName).getStations().size());
    }

    @Test
    void getAllStationsCorrespondence() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"Châtelet"})
    void getBestStations(String stationName) {
        assertEquals(5, service.getBestStations().size());
        StationDTO station = new StationDTO(stationName, 0, 0);
        StationCorrespondence stationCorrespondence = new StationCorrespondence(station, new HashSet<>());
        System.out.println(service.getBestStations());
        assertTrue(service.getBestStations().contains(stationCorrespondence));
    }
}
