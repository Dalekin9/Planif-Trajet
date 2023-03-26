package com.planifcarbon.backend.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class MetroMapTest extends Assertions {
    static Stream<Arguments> generateDataNode() {
        return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class), Arguments.of("Maison", 1.0, 2.0, PersonalizedNode.class));
    }

    @ParameterizedTest
    @MethodSource("generateDataNode")
    public void testAddNode(String name, double la, double lo, Class<? extends Node> cl) {
        MetroMap map = new MetroMap();
        map.addNode(name, la, lo, cl);
        assertEquals(1, map.getNodes().size());
        assertTrue(map.getNode(name) != null);
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
        map.addSegmentMetro("A", "B", 10.0, 4.0, "1");
        assertEquals(1, map.getSegments("A").size());
    }

    @Test
    public void testAddSegmentThrow() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        assertThrows(Exception.class, () -> map.addSegmentMetro("A", "B", 10.0, 4.0, "1"));
    }

    @Test
    public void testAddSegmentThrow2() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        assertThrows(IllegalArgumentException.class, () -> map.addSegmentMetro("A", "B", 10.0, 4.0, null));
    }

    @Test
    public void testAddSegmentWalk() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentWalk("A", "B", 10.0);
    }

    // TODO test createLine

}
