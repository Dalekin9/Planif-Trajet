package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.*;

/**
 * This class is a JUnit test class for the StationTest class.
 */
public class StationTest extends Assertions {

    /**
     * Test the creation of a Station object with a given name and coordinates
     * and verify that the name and coordinates can be retrieved correctly.
     *
     * @param name  a String representing the name of the Station
     * @param la    a double representing the latitude of the Station's coordinates
     * @param lo    a double representing the longitude of the Station's coordinates
     */
    @ParameterizedTest
    @CsvSource({"S1,1,2", "S2,0,0", "S3,-1,0", "Nation,0,180", "StO,0,-180", "PtChap,90,-180"})
    public void testStation(String name, double la, double lo) {
        Station s = new Station(name, la, lo);
        assertEquals(name, s.getName());
        assertEquals(la, s.getCoordinates().getLatitude());
        assertEquals(lo, s.getCoordinates().getLongitude());
    }

    /**
     * Test that a Station is correctly identified as being part of the metro network.
     *
     * @param name  a String representing the name of the Station
     * @param la    a double representing the latitude of the Station's coordinates
     * @param lo    a double representing the longitude of the Station's coordinates
     */
    @ParameterizedTest
    @CsvSource({"S1,1,2"})
    public void testIsInMetro(String name, double la, double lo) {
        Station s = new Station(name, la, lo);
        assertTrue(s.isInMetro());
    }

    /**
     * Test that the schedules for a Station can be retrieved correctly.
     *
     * @param name  a String representing the name of the Station
     * @param size  an int representing the expected size of the schedules Map
     */
    @ParameterizedTest
    @CsvSource({"Saint-François-Xavier, 4"})
    public void testGetSchedules(String name, int size) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());

        Station station = map.getStationByName(name);
        assertNotNull(station.getSchedules());
        Map<ScheduleKey, Integer> sh = station.getSchedules();
        assertEquals(size, station.getSchedules().size());
    }
}
