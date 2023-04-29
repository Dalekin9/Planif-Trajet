package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SegmentWalkTest {

    @ParameterizedTest
    @ValueSource(doubles = {-2, -0.252})
    public void checkDistanceDurationMustBeGreatherThen0(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        assertThrows(IllegalArgumentException.class, () -> new SegmentWalk(node1, node2, distance));
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetDistanceMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(distance, t.getDistance());
    }

    @ParameterizedTest
    @CsvSource({"1.23, 1006363", "4.56789, 3737364", "1.022, 836181", "20, 16363636"})
    public void testGetDurationMethode(double distance, int time) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(time / 1000, t.getDuration());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetStartPointMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(node1, t.getStartPoint());
        assertNotEquals(node2, t.getStartPoint());
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.23, 4.56789, 1.022, 20})
    public void testGetEndPointMethode(double distance) {
        Node node1 = new Station("st1", 10.15, 0);
        Node node2 = new Station("st2", 1.98, 2.14);
        SegmentWalk t = new SegmentWalk(node1, node2, distance);
        assertEquals(node2, t.getEndPoint());
        assertNotEquals(node1, t.getEndPoint());
    }

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
