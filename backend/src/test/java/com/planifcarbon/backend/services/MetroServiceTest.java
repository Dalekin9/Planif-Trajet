package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.MetroLineStationSchedulesDTO;
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the MetroService class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {MetroService.class, MetroMap.class})
@TestPropertySource(locations = "classpath:application-tests.properties")
class MetroServiceTest {

    @Autowired
    private MetroService service;

    /**
     * Tests the getMetros method of the MetroService class.
     * Ensures that the number of metro lines returned by the method is the expected one.
     */
    @Test
    void getMetros() {
        int nbLines = 16;
        assertEquals(nbLines, service.getMetros().size());
    }

    /**
     * Tests the getMetroByName method of the MetroService class.
     * Ensures that a metro line exists for the given metro name.
     */
    @ParameterizedTest
    @ValueSource(strings = {"1", "5"})
    void testGetMetroByName(String metroName) {
        assertNotNull(service.getMetroByName(metroName));
    }


    /**
     * Tests the getMetroByName method of the MetroService class.
     * Ensures that no metro line exists for the given metro name.
     */
    @ParameterizedTest
    @ValueSource(strings = {"17"})
    void testGetMetroByNameNotExists(String metroName) {
        assertEquals(0, service.getMetroByName(metroName).getStations().size());
    }

    /**
     * Tests the getAllStationsCorrespondence method of the MetroService class.
     */
    @Test
    void getAllStationsCorrespondence() {
        assertNotNull(service.getAllStationsCorrespondence());
    }

    /**
     * Tests the getBestStations method of the MetroService class.
     * Ensures that the method returns the expected number of stations and that a given station is among them.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Châtelet"})
    void getBestStations(String stationName) {
        assertEquals(5, service.getBestStations().size());
        NodeDTO station = new NodeDTO(stationName, 0, 0);
        StationCorrespondence stationCorrespondence = new StationCorrespondence(station, new HashSet<>());
        assertTrue(service.getBestStations().contains(stationCorrespondence));
    }

    @ParameterizedTest
    @CsvSource({"Bercy, 6", "Gare du Nord, 4"})
    void getLineSchedulesForStation(String stationName, String metroName) {
        MetroLineStationSchedulesDTO result = service.getLineSchedulesForStation(stationName, metroName);
        assertNotNull(result);
        assertFalse(result.getSchedules().isEmpty());
    }
}
