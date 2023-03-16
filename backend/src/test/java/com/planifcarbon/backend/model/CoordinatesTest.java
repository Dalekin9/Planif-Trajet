package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordinatesTest extends Assertions {

    // TODO move values out of tests.

    // Test for the constructor, the getters and the equals method
    @Test
    public void testCoordinates() {
        Coordinates c = new Coordinates(1, 2);
        assertEquals(1, c.getLatitude());
        assertEquals(2, c.getLongitude());
    }
    @Test
    public void testCoordinatesWithNegativeValue() {
        Coordinates c = new Coordinates(-1.34, 0);
        assertEquals(-1.34, c.getLatitude());
        assertEquals(0, c.getLongitude());
    }
    @Test
    public void testCoordinatesWithCorrectLongitude() {
        assertDoesNotThrow(() -> new Coordinates(90, 0));
        assertDoesNotThrow(() -> new Coordinates(0, 0));
        assertDoesNotThrow(() -> new Coordinates(-1, 0));
    }
    @Test
    public void testCoordinatesWithCorrectLatitude() {
        assertDoesNotThrow(() -> new Coordinates(0, 180));
        assertDoesNotThrow(() -> new Coordinates(0, -180));
        assertDoesNotThrow(() -> new Coordinates(0, 0));
        assertDoesNotThrow(() -> new Coordinates(0, 179.999999999999));
        assertDoesNotThrow(() -> new Coordinates(0, -179.999999999999));
    }
    @Test
    public void testCoordinatesWithCorrectLatitudeAndLongitude() {
        assertDoesNotThrow(() -> new Coordinates(90, -180));
        assertDoesNotThrow(() -> new Coordinates(80.1, -80.4567));
    }
    @Test
    public void testCoordinatesWithBadLongitude() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(90.1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(-91, 0));
    }
    @Test
    public void testCoordinatesWithBadLatitude() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, 180.00000000009));
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(0, -18142531.1584152));
    }


    // Test for the toString method
    @Test
    public void testToStringIntCoordinates() {
        Coordinates c = new Coordinates(1, 2);
        assertEquals("1.0, 2.0", c.toString());
    }
    @Test
    public void testToStringDoubleCoordinates() {
        Coordinates c = new Coordinates(-1.2536, 2.456);
        assertEquals("-1.2536, 2.456", c.toString());
    }
}
