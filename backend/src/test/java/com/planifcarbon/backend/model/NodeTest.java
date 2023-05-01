package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class contains JUnit tests for the {@link Node} class.
 */
public class NodeTest extends Assertions {

    /**
     * Tests the creation and getters of a {@link Node} object.
     *
     * @param name the name of the node
     * @param la the latitude coordinate of the node
     * @param lo the longitude coordinate of the node
     */
    @ParameterizedTest
    @CsvSource({"A,1,2", "fezhejtykt,0,0", "é^^!§,-1,0", "aiuêz,0,180"})
    public void testNode(String name, double la, double lo) {
        Node n = new NodeForTest(name, la, lo);
        assertEquals(name, n.getName());
        assertEquals(la, n.getCoordinates().getLatitude());
        assertEquals(lo, n.getCoordinates().getLongitude());
    }

    /**
     * Tests the equality of two {@link Node} objects.
     *
     * @param name the name of the node
     * @param la the latitude coordinate of the node
     * @param lo the longitude coordinate of the node
     */
    @ParameterizedTest
    @CsvSource({"A,1,2", "fezhejtykt,0,0", "é^^!§,-1,0", "aiuêz,0,180"})
    public void testEquals(String name, double la, double lo) {
        Node n = new NodeForTest(name, la, lo);
        Node n2 = new NodeForTest(name, la, lo);
        assertEquals(n, n);
        assertEquals(n2, n);
    }

    /**
     * Tests the inequality of two {@link Node} objects.
     *
     * @param name the name of the first node
     * @param la the latitude coordinate of the first node
     * @param lo the longitude coordinate of the first node
     * @param name2 the name of the second node
     * @param la2 the latitude coordinate of the second node
     * @param lo2 the longitude coordinate of the second node
     */
    @ParameterizedTest
    @CsvSource({"A,1,2,B,1,2", "A,1,2,B,2,3"})
    public void testNotEquals(String name, double la, double lo, String name2, double la2, double lo2) {
        assertNotEquals(new NodeForTest(name, la, lo), new NodeForTest(name2, la2, lo2));
    }

    // null name or bad coordinates
    /**
     * Tests the constructor of a {@link Node} object with invalid arguments.

     * @param name the name of the node (may be null)
     * @param la the latitude coordinate of the node
     * @param lo the longitude coordinate of the node
     */
    @ParameterizedTest
    @CsvSource({",0, 0", "tryu,-91, 0", "Nation,0, 180.00000000009", "pom,0, -18142531.1584152"})
    public void testThrows(String name, double la, double lo) {
        assertThrows(IllegalArgumentException.class, () -> new NodeForTest(name, la, lo));
    }

    /**
     * Tests the string representation of a {@link Node} object.
     *
     * @param name the name of the node
     * @param la the latitude coordinate of the node
     * @param lo the longitude coordinate of the node
     * @param expected the expected string representation of the node
     */
    @ParameterizedTest
    @CsvSource({"A,1.23,4.56789,'A: 1.23, 4.56789'", "fezhejtykt,0,0,'fezhejtykt: 0.0, 0.0'", "é^^!§,-1,0,'é^^!§: -1.0, 0.0'"})
    public void testToString(String name, double la, double lo, String expected) {
        Node n = new NodeForTest(name, la, lo);
        assertEquals(expected, n.toString());
    }

    /**
     * Tests the {@link Node#isInMetro()} method.
     */
    @ParameterizedTest
    @CsvSource({"A,0,0"})
    public void testIsInMetro() {
        Node n = new NodeForTest("A", 0, 0);
        assertFalse(n.isInMetro());
    }
}
