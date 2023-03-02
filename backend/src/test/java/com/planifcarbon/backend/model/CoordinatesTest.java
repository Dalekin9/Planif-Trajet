package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CoordinatesTest extends Assertions {

    // Test for the constructor, the getters and the equals method
    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testCoordinates(double la, double lo) {
        Coordinates c = new Coordinates(la, lo);
        assertEquals(la, c.getLatitude());
        assertEquals(lo, c.getLongitude());
    }

    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testDoesNotThrow(double la, double lo) {

        assertDoesNotThrow(() -> new Coordinates(la, lo));
    }

    @ParameterizedTest
    @CsvSource({"90.1, 0", "-91, 0", "0, 180.00000000009", "0, -18142531.1584152"})
    public void testThrows(double la, double lo) {

        assertThrows(IllegalArgumentException.class, () -> new Coordinates(la, lo));
    }


    // Test for the toString method
    @ParameterizedTest
    @CsvSource({"1,2,'1.0, 2.0'", "-1.2536, 2.456, '-1.2536, 2.456'"})
    public void testToStringIntCoordinates(double la, double lo, String expected) {
        Coordinates c = new Coordinates(la, lo);
        assertEquals(expected, c.toString());
    }

    // TODO equals tests

    @ParameterizedTest
    @CsvSource({"1,2,1,2", "0,0,0,0", "-1,0,-1,0", "0,180,0,180", "0,-180,0,-180", "90,-180,90,-180", "80.1,-80.4567,80.1,-80.4567"})
    public void testEquals(double la1, double lo1, double la2, double lo2) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertEquals(c1, c2);
    }

    @ParameterizedTest
    @CsvSource({"0,0,0,0.00000001", "-1,0,-1,1", "0,180,0,17", "0,-180,0,-179", "90,-180,90,-179", "80.1,-80.4567,80.1,-80.4568"})
    public void testNotEquals(double la1, double lo1, double la2, double lo2) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testNotEqualsNull() {
        Coordinates c1 = new Coordinates(1, 2);
        assertNotEquals(null, c1);
    }

    @Test
    public void testNotEqualsOtherClass() {
        Coordinates c1 = new Coordinates(1, 2);
        assertNotEquals(new Object(), c1);
    }

    @Test
    public void testNotEqualsString() {
        Coordinates c1 = new Coordinates(1, 2);
        assertNotEquals("1,2", c1);
    }

    @ParameterizedTest
    @CsvSource({"0,0,0,0,0", "1,0,0,0,111", "1.34, 0, 0, 20, 2231"})
    public void testDistanceTo(double la1, double lo1, double la2, double lo2, double distance) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertEquals(distance, (int) c1.distanceTo(c2));
    }
}
