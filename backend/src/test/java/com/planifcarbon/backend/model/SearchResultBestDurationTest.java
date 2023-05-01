package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class tests the creation and equality of SearchResultBestDuration objects.
 */
public class SearchResultBestDurationTest extends Assertions {

    /**
     * Tests the SearchResultBestDuration class by creating a new instance of SearchResultBestDuration
     * and verifying that it is not null.
     *
     * @param stationName the name of the station to search from
     * @param time the time to search from
     * @param lineName the name of the metro line to search
     */
    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testSearchResultBestDuration(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        assertNotNull(new SearchResultBestWeight(station, time, line));
    }

    /**
     * Test the getNodeFrom method of SearchResultBestDuration class.
     * Asserts that a SearchResultBestDuration object can be created without throwing an exception,
     * and that the getNodeFrom method returns a non-null value.
     *
     * @param stationName a String representing the name of the station
     * @param time an int representing the time value
     * @param lineName a String representing the name of the metro line
     */
    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testGetNodeFrom(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);

        assertNotNull(new SearchResultBestWeight(station, time, line));
    }

    /**
     * Test method for {@link SearchResultBestWeight#getWeight()}.
     * Tests that the getArrivalTime method of SearchResultBestDuration returns a non-null value.
     *
     * @param stationName the name of the station to test with
     * @param time the time to test with
     * @param lineName the name of the metro line to test with
     */
    @ParameterizedTest
    @CsvSource({"Château de Vincennes, 5467, 1 variant 1"})
    public void testGetArrivalTime(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        SearchResultBestWeight rez = new SearchResultBestWeight(station, time, line);
        assertNotNull(rez.getWeight());
    }

    /**
     * Tests the `getMetroLine()` method of the `SearchResultBestDuration` class.
     *
     * The test uses the given station name, time and line name to create a new `SearchResultBestDuration` instance and
     * asserts that the `getMetroLine()` method returns a non-null value.
     *
     * @param stationName the name of the station
     * @param time the time of the search
     * @param lineName the name of the metro line
     */
    @ParameterizedTest
    @CsvSource({"Nation, 5670, 1 variant 2"})
    public void testGetMetroLine(String stationName, int time, String lineName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        MetroLine line = map.getLines().get(lineName);
        SearchResultBestWeight rez = new SearchResultBestWeight(station, time, line);
        assertNotNull(rez.getMetroLine());
    }
}
