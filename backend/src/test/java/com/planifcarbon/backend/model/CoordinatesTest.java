package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the {@link Coordinates} class.
 */
public class CoordinatesTest extends Assertions {

    /**
     * Tests the creation of a {@link Coordinates} object with valid latitude and longitude values.
     * @param la The latitude of the coordinates.
     * @param lo The longitude of the coordinates.
     */
    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testCoordinates(double la, double lo) {
        Coordinates c = new Coordinates(la, lo);
        assertEquals(la, c.getLatitude());
        assertEquals(lo, c.getLongitude());
    }

    /**
     * Tests that creating a {@link Coordinates} object with valid latitude and longitude values does not throw an exception.
     * @param la The latitude of the coordinates.
     * @param lo The longitude of the coordinates.
     */
    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testDoesNotThrow(double la, double lo) {
        assertDoesNotThrow(() -> new Coordinates(la, lo));
    }

    /**
     * Tests that creating a {@link Coordinates} object with invalid latitude and longitude values throws an {@link IllegalArgumentException}.
     *
     * @param la The latitude of the coordinates.
     * @param lo The longitude of the coordinates.
     */
    @ParameterizedTest
    @CsvSource({"90.1, 0", "-91, 0", "0, 180.00000000009", "0, -18142531.1584152"})
    public void testThrows(double la, double lo) {
        assertThrows(IllegalArgumentException.class, () -> new Coordinates(la, lo));
    }

    /**
     * Test the toString method of the Coordinates class with integer coordinates.
     *
     * @param la The latitude of the coordinates.
     * @param lo The longitude of the coordinates.
     * @param expected The expected string representation of the coordinates.
     */
    @ParameterizedTest
    @CsvSource({"1,2,'1.0, 2.0'", "-1.2536, 2.456, '-1.2536, 2.456'"})
    public void testToStringIntCoordinates(double la, double lo, String expected) {
        Coordinates c = new Coordinates(la, lo);
        assertEquals(expected, c.toString());
    }

    /**
     * Test the equals method of the Coordinates class with valid equal coordinates.
     *
     * @param la1 The latitude of the first set of coordinates.
     * @param lo1 The longitude of the first set of coordinates.
     * @param la2 The latitude of the second set of coordinates.
     * @param lo2 The longitude of the second set of coordinates.
     */
    @ParameterizedTest
    @CsvSource({"1,2,1,2", "0,0,0,0", "-1,0,-1,0", "0,180,0,180", "0,-180,0,-180", "90,-180,90,-180", "80.1,-80.4567,80.1,-80.4567"})
    public void testEquals(double la1, double lo1, double la2, double lo2) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertEquals(c1, c2);
    }

    /**
     * Test the equals method of the Coordinates class with invalid not equal coordinates.
     *
     * @param la1 The latitude of the first set of coordinates.
     * @param lo1 The longitude of the first set of coordinates.
     * @param la2 The latitude of the second set of coordinates.
     * @param lo2 The longitude of the second set of coordinates.
     */
    @ParameterizedTest
    @CsvSource({"0,0,0,0.00000001", "-1,0,-1,1", "0,180,0,17", "0,-180,0,-179", "90,-180,90,-179", "80.1,-80.4567,80.1,-80.4568"})
    public void testNotEquals(double la1, double lo1, double la2, double lo2) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertNotEquals(c1, c2);
    }

    /**
     * Test the equals method of the Coordinates class with null input.
     *
     * @param la The latitude of the coordinates.
     * @param lo The longitude of the coordinates.
     */
    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testNotEqualsNull(double la, double lo) {
        Coordinates c1 = new Coordinates(la, lo);
        assertNotEquals(null, c1);
    }


    /**
     * This method tests if a Coordinates object is not equal to an object of a different class.
     *
     * @param la the latitude of the coordinates to test.
     * @param lo the longitude of the coordinates to test.
     * @throws AssertionError if the coordinates object is equal to an object of a different class.
     */
    @ParameterizedTest
    @CsvSource({"1,2", "0,0", "-1,0", "0,180", "0,-180", "90,-180", "80.1,-80.4567"})
    public void testNotEqualsOtherClass(double la, double lo) {
        Coordinates c1 = new Coordinates(la, lo);
        assertNotEquals(new Object(), c1);
    }

    /**
     * Test method to verify that two Coordinates objects are not equal when compared to a String.
     *
     * @param la the latitude of the Coordinates object being tested
     * @param lo the longitude of the Coordinates object being tested
     * @param other the String being compared to the Coordinates object
     */
    @ParameterizedTest
    @CsvSource({"1,2,'1,2'", "0,0,", "-1,0,artzret", "0,180,MPO"})
    public void testNotEqualsString(double la, double lo, String other) {
        Coordinates c1 = new Coordinates(la, lo);
        assertNotEquals(c1, other);
    }


    /**
     * Tests for the {@link Coordinates#distanceTo(Coordinates)} method.
     *
     * @param la1 the latitude of 1st the Coordinates object being tested
     * @param lo1 the longitude of 1st the Coordinates object being tested
     * @param la2 the latitude of the 2nd Coordinates object being tested
     * @param lo2 the longitude of the 2ns Coordinates object being tested
     * @param distance distance between the two coordinates.
     */
    @ParameterizedTest
    @CsvSource({"0,0,0,0,0", "1,0,0,0,111", "1.34, 0, 0, 20, 2231"})
    public void testDistanceTo(double la1, double lo1, double la2, double lo2, double distance) {
        Coordinates c1 = new Coordinates(la1, lo1);
        Coordinates c2 = new Coordinates(la2, lo2);
        assertEquals(distance, (int) c1.distanceTo(c2));
    }
}
