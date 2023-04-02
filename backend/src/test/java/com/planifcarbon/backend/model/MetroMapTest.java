package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MetroMap.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class MetroMapTest {

    static Stream<Arguments> generateDataNode() {
        return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class), Arguments.of("Maison", 1.0, 2.0, PersonalizedNode.class));
    }

    @ParameterizedTest
    @MethodSource("generateDataNode")
    public void testAddNode(String name, double la, double lo, Class<? extends Node> cl) {
        MetroMap map = new MetroMap();
        map.addNode(name, la, lo, cl);
        assertEquals(1, map.getNodes().size());
    }

    @ParameterizedTest
    @CsvSource({"A, 1.0, 2.0", "Maison, 1.0, 2.0"})
    public void testAddNodeThrows(String name, double la, double lo) {
        MetroMap map = new MetroMap();
        assertThrows(IllegalArgumentException.class, () -> map.addNode(name, la, lo, null));
    }

    @Test
    public void testAddSegmentMetro() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, "1");
        assertEquals(1, map.getSegments(new NodeForTest("A", 0.0, 0.0)).size());
    }

    @Test
    public void testAddSegmentThrow() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        assertDoesNotThrow(() -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, "1"));
    }

    @Test
    public void testAddSegmentThrow2() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        assertThrows(IllegalArgumentException.class,
                () -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, null));
    }

    @Test
    public void testAddSegmentWalk() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentWalk(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0);
    }

    @ParameterizedTest
    @CsvSource({"Argentine, 4, 311"})
    public void testMetroMapDataCreation(String stationName, int nbSegmentsMetro, int nbSegments) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());
        assertNotEquals(0, map.getLines().size());
        assertNotEquals(0, map.getStations().size());
        assertNotEquals(0, map.getNodes().size());
        assertEquals(93, map.getLines().size());
        assertEquals(308, map.getStations().size());
        assertEquals(308, map.getNodes().size());
        List<Station> stations = new ArrayList<>();
        map.getStations().values().forEach(station -> {
            assertFalse(stations.contains(station));
            stations.add(station);
        });
        List<MetroLine> metroLines = new ArrayList<>();
        map.getLines().values().forEach(line -> {
            assertFalse(metroLines.contains(line));
            assertNotNull(line.getSchedules());
            assertFalse(line.getSchedules().isEmpty());
            metroLines.add(line);
        });
        assertEquals(nbSegmentsMetro, map.getSegmentsMetro(new NodeForTest(stationName, 0.0, 0.0)).size());
        assertEquals(nbSegments, map.getSegments(new NodeForTest(stationName, 0.0, 0.0)).size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Argentine"})
    public void testGetStationByName(String stationName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        assertNotNull(map.getStationByName(stationName));
        assertNotEquals(0, station.getSchedules().size());
        assertEquals(4, station.getSchedules().size());
        System.out.println(station.getSchedules());
    }
}
