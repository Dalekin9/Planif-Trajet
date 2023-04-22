package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.*;

public class StationTest extends Assertions {
    @ParameterizedTest
    @CsvSource({"S1,1,2", "S2,0,0", "S3,-1,0", "Nation,0,180", "StO,0,-180", "PtChap,90,-180"})
    public void testStation(String name, double la, double lo) {
        Station s = new Station(name, la, lo);
        assertEquals(name, s.getName());
        assertEquals(la, s.getCoordinates().getLatitude());
        assertEquals(lo, s.getCoordinates().getLongitude());
    }

    @ParameterizedTest
    @CsvSource({"S1,1,2"})
    public void testIsInMetro(String name, double la, double lo) {
        Station s = new Station(name, la, lo);
        assertTrue(s.isInMetro());
    }

    @ParameterizedTest
    @CsvSource({"Saint-François-Xavier, 4"})                                                   // AZH need to check some more stations
    public void testGetSchedules(String name, int size) {
        System.out.println("\n\n========= Test getSchedules for Station " + name + " ====================");
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());

        Station station = map.getStationByName(name);
        assertNotNull(station.getSchedules());
        Map<ScheduleKey, Integer> sh = station.getSchedules();
        sh.forEach((key, val) -> {
            System.out.println(key.toString() + " : " + val.toString());
        });
        assertEquals(size, station.getSchedules().size());
        System.out.println("========= End Test getSchedules ====================\n\n");
    }


    @ParameterizedTest
    @CsvSource({"Duroc, 114, Châtillon-Montrouge, 13 variant 4, 7"})
    public void testGetTimeTable(String stName, int nb, String schKeyName, String lineName, int size) {
        System.out.println("\n\n========= Test getTimeTable for Station " + stName + " ====================");
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());

        Station station = map.getStationByName(stName);
        assertNotNull(station.getTimeTable());
        Map<ScheduleKey, List<Integer>> timeTable = station.getTimeTable();
        timeTable.forEach((key, val) -> {
            System.out.println(key.toString() + " : " + Arrays.toString(val.toArray()));
        });
        assertEquals(timeTable.size(), size);
        assertNotNull(map.getScheduleKeyByName(schKeyName));
        ScheduleKey schKey = map.getScheduleKeyByName(schKeyName);

        Map<String, MetroLine> lines = map.getLines();
        MetroLine line = lines.get(lineName);

        assertEquals(schKey.getMetroLine(), line);
        System.out.println("========= End Test GetTimeTable ====================\n\n");
    }
}
