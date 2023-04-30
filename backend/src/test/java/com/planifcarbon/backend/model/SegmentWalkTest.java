package com.planifcarbon.backend.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


/**
 * This class is a JUnit test class for the SegmentWalk class.
 */
public class SegmentWalkTest extends Assertions {

    /**
     * This test method checks if an IllegalArgumentException is thrown when the distance parameter in the
     * SegmentWalk constructor is less than or equal to 0.
     *
     * @param distance the distance between two points in a segment of a walk
     */
    @ParameterizedTest
    @ValueSource(doubles = {-2, -0.252})
    public void checkDistanceDurationMustBeGreatherThen0(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        assertThrows(IllegalArgumentException.class, () -> new SegmentWalk(node1, node2, distance));
    }

    /**
     * This test method tests the functionality of the getDistance() method in the SegmentWalk class. It tests if the
     * method returns the correct distance value set in the SegmentWalk constructor.
     *
     * @param distance the distance between two points in a segment of a walk
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetDistanceMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(distance, t.getDistance());
    }

    /**
     * This test method tests the functionality of the getDuration() method in the SegmentWalk class. It tests if the
     * method returns the correct duration value based on the distance value set in the SegmentWalk constructor.
     *
     * @param distance the distance between two points in a segment of a walk
     * @param time     the expected time taken to travel the distance
     */
    @ParameterizedTest
    @CsvSource({"1.23, 1006363", "4.56789, 3737364", "1.022, 836181", "20, 16363636"})
    public void testGetDurationMethode(double distance, int time) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(time / 1000, t.getDuration());
    }

    /**
     * This test method tests the functionality of the getStartPoint() method in the SegmentWalk class. It tests if the
     * method returns the correct start point of the segment set in the SegmentWalk constructor.
     *
     * @param distance the distance between two points in a segment of a walk
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetStartPointMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(node1, t.getStartPoint());
        assertNotEquals(node2, t.getStartPoint());
    }

    /**
     * Test the getEndPoint method of the SegmentWalk class using different distances.
     *
     * @param distance the distance of the SegmentWalk object
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetEndPointMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(node2, t.getEndPoint());
        assertNotEquals(node1, t.getEndPoint());
    }

    /**
     * Test the isEquals method of the SegmentWalk class using different distances.
     *
     * @param distance the distance of the SegmentWalk objects
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testIsEqualsMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t1 = new SegmentWalk(node1, node2, distance);
        SegmentWalk t2 = new SegmentWalk(node1, node2, distance);
        SegmentWalk t3 = new SegmentWalk(node2, node1, distance);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }
}
