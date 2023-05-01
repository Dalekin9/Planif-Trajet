package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the {@link SegmentMetro} class.
 */
public class SegmentMetroTest extends Assertions {

    /**
     * Verifies that the distance and duration passed to the constructor of {@link SegmentMetro} are greater than zero.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"2, -1, doesntmatter", "-2, -4, doesntmatter"})
    public void checkDistanceDurationMustBeGreatherThen0(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        assertThrows(IllegalArgumentException.class, () -> new SegmentMetro(node1, node2, distance, duration, line));
    }

    /**
     * Verifies that the {@link SegmentMetro#getLine()} method returns the correct line number.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"1.022, 10, sdkfjhsdf", "20, 10, Begge45tger"})
    public void testGetLineMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(line, t.getLine());
    }

    /**
     * Verifies that the {@link SegmentMetro#getDistance()} method returns the correct distance.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "20, 10, doesntmatter"})
    public void testGetDistanceMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(distance, t.getDistance());
    }

    /**
     * Verifies that the {@link SegmentMetro#getDuration()} method returns the correct duration.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testGetDurationMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(duration, t.getDuration());
    }

    /**
     * Verifies that the {@link SegmentMetro#getStartPoint()} method returns the correct starting node.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"1.32, 10, doesntmatter", "1.034, 10, doesntmatter"})
    public void testGetStartPointMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(node1, t.getStartPoint());
        assertNotEquals(node2, t.getStartPoint());
    }

    /**
     * Verifies that the {@link SegmentMetro#getEndPoint()} method returns the correct ending node.
     *
     * @param distance the distance between two metro stations
     * @param duration the duration of travel between two metro stations
     * @param line     the line number of the metro
     */
    @ParameterizedTest
    @CsvSource({"0.92, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testGetEndPointMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t = new SegmentMetro(node1, node2, distance, duration, line);
        assertEquals(node2, t.getEndPoint());
        assertNotEquals(node1, t.getEndPoint());
    }

    /**
     * Tests the {@link SegmentMetro} class' equals method.
     * Uses parameterized testing with CSV source data for distance, duration, and line values.
     *
     * @param distance the distance value for the segment being tested
     * @param duration the duration value for the segment being tested
     * @param line metro line to use in test.
     */
    @ParameterizedTest
    @CsvSource({"1.022, 10, doesntmatter", "2.034, 10, doesntmatter"})
    public void testIsEqualsMethode(double distance, int duration, String line) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentMetro t1 = new SegmentMetro(node1, node2, distance, duration, line);
        SegmentMetro t2 = new SegmentMetro(node1, node2, distance, duration, line);
        SegmentMetro t3 = new SegmentMetro(node2, node1, distance, duration, line);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }
}
