package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SegmentMetroTest {

    @ParameterizedTest
    @CsvSource({"0, 0, doesntmatter", "-2, 0, doesntmatter", "2, -1, doesntmatter", "-2, -4, doesntmatter",})
    public void checkDistanceDurationMustBeGreatherThen0(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        assertThrows(IllegalArgumentException.class, () -> new SegmentMetro(node1, node2, distance, duration, line));
    }

    @ParameterizedTest
    @CsvSource({"1.022, 10, sdkfjhsdf", "20, 10, Begge45tger"})
    public void testGetLineMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(line, t.getLine());
    }

    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "20, 10, doesntmatter"})
    public void testGetDistanceMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(distance, t.getDistance());
    }

    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testGetDurationMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(duration, t.getDuration());
    }

    @ParameterizedTest
    @CsvSource({"1.32, 10, doesntmatter", "1.034, 10, doesntmatter"})
    public void testGetStartPointMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(node1, t.getStartPoint());
        assertNotEquals(node2, t.getStartPoint());
    }

    @ParameterizedTest
    @CsvSource({"0.92, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testGetEndPointMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(node2, t.getEndPoint());
        assertNotEquals(node1, t.getEndPoint());
    }

    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testIsEqualsMethode(double distance, double duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t1 = new SegmentMetro(node1, node2, distance, duration, line);
        SegmentMetro t2 = new SegmentMetro(node1, node2, distance, duration, line);
        SegmentMetro t3 = new SegmentMetro(node2, node1, distance, duration, line);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }
}
