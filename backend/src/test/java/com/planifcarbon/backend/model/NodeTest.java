package com.planifcarbon.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NodeTest extends Assertions {
    @ParameterizedTest
    @CsvSource({"A,1,2", "fezhejtykt,0,0", "é^^!§,-1,0", "aiuêz,0,180"})
    public void testNode(String name, double la, double lo) {
        Node n = new Node(name, la, lo);
        assertEquals(name, n.getName());
        assertEquals(la, n.getCoordinates().getLatitude());
        assertEquals(lo, n.getCoordinates().getLongitude());
    }

    @ParameterizedTest
    @CsvSource({"A,1,2", "fezhejtykt,0,0", "é^^!§,-1,0", "aiuêz,0,180"})
    public void testEquals(String name, double la, double lo) {
        Node n = new Node(name, la, lo);
        assertTrue(n.equals(n));
        assertTrue(n.equals(new Node(name, la, lo)));
    }

    @ParameterizedTest
    @CsvSource({"A,1,2,B,1,2", "A,1,2,A,2,2", "A,1,2,A,1,3", "A,1,2,B,2,3"})
    public void testNotEquals(String name, double la, double lo, String name2, double la2, double lo2) {
        assertFalse(new Node(name, la, lo).equals(new Node(name2, la2, lo2)));
    }

    @ParameterizedTest
    @CsvSource({"a,1,2,", "a,1,2,a"})
    public void testNotEqualsString(String name, double la, double lo, String other) {
        Node n = new Node(name, la, lo);
        assertNotEquals(n, other);
    }

    // null name or bad coordinates
    @ParameterizedTest
    @CsvSource({",0, 0", "tryu,-91, 0", "Nation,0, 180.00000000009", "pom,0, -18142531.1584152"})
    public void testThrows(String name, double la, double lo) {

        assertThrows(IllegalArgumentException.class, () -> new Node(name, la, lo));
    }

    @ParameterizedTest
    @CsvSource({"A,1.23,4.56789,'A: 1.23, 4.56789'", "fezhejtykt,0,0,'fezhejtykt: 0.0, 0.0'", "é^^!§,-1,0,'é^^!§: -1.0, 0.0'"})
    public void testToString(String name, double la, double lo, String expected) {
        Node n = new Node(name, la, lo);
        assertEquals(expected, n.toString());
    }
}
