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
    @CsvSource({"Saint-François-Xavier"})                                                   // AZH need to check some more stations
    public void testGetSchedules(String name) {
       // System.out.println("\n\n========= Test getSchedules for Station " + name + " ====================");
       // MetroMap map = new MetroMap();
       // assertDoesNotThrow(map::initializeFields);
       // assertNotNull(map.getLines());
       // assertNotNull(map.getStations());
       // assertNotNull(map.getGraph());
////
       // Station station = map.getStationByName(name);
       // assertNotNull(station.getSchedules());
       // Map<ScheduleKey, Integer> sh = station.getSchedules();
       // sh.forEach((key, val) -> {
       //     System.out.println(key.toString() + " : " + val.toString());
       // });
       // System.out.println("Nb Schedules = " + sh.size());
       // // assertEquals( ??, station.getSchedules().size());         // AZH how many terminusStation & depart times has to be ?
       //                                                             // Why only 4 out of 7 variants for Line 13 are present
       //                                                             // and only 3 out of 4 variants for Line 10 are present
       // System.out.println("========= End Test getSchedules for Station ====================\n\n");
    }
}
