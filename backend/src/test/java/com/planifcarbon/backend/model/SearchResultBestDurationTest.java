package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}
