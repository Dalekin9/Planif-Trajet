package com.planifcarbon.backend.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MetroMapTest extends Assertions {
    static Stream<Arguments> generateData1() { return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class)); }

    @ParameterizedTest
    @MethodSource("generateData1")
    public void testAddNode(String name, double la, double lo, Class<? extends Node> cl) {
        MetroMap map = new MetroMap();
        // map.addNode(name, la, lo, cl);
        // assertEquals(1, map.getNodes().size());
        // assertTrue(map.getNode(name) != null);
    }
}
