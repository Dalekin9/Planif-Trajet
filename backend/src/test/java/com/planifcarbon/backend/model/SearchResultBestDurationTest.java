package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.*;

public class SearchResultBestDurationTest extends Assertions {
    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testSearchResultBestDuration(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        assertNotNull(new SearchResultBestDuration(station, time, line));
    }

    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testGetNodeFrom(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);

        assertNotNull(new SearchResultBestDuration(station, time, line));
    }

    @ParameterizedTest
    @CsvSource({"Château de Vincennes, 5467, 1 variant 1"})
    public void testGetArrivalTime(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        SearchResultBestDuration rez = new SearchResultBestDuration(station, time, line);
        assertNotNull(rez.getArrivalTime());
    }

    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testGetMetroLine(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        SearchResultBestDuration rez = new SearchResultBestDuration(station, time, line);
        assertNotNull(rez.getMetroLine());
    }

    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2", "Château de Vincennes, 5467, 1 variant 1"})
    public void testReplace(String stationName1, int time1, String lineName1, String stationName2, int time2, String lineName2)
    {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station1 = map.getStationByName(stationName1);
        Station station2 = map.getStationByName(stationName2);
        MetroLine line1 = map.getLines().get(lineName1);
        MetroLine line2 = map.getLines().get(lineName2);

        SearchResultBestDuration rez1 = new SearchResultBestDuration(station1, time1, line1);
        Node n = rez1.getNodeFrom();
        int t = rez1.getArrivalTime();
        MetroLine l = rez1.getMetroLine();

        rez1.replace((Node) station2, time2, line2);

        assertNotEquals(rez1.getNodeFrom(), n);
        assertNotEquals(rez1.getArrivalTime(), t);
        assertNotEquals(rez1.getMetroLine(), l);
    }
}
