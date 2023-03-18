package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PersonalizedNodeTest {
    @ParameterizedTest
    @CsvSource({"S1,1,2", "S2,0,0", "S3,-1,0", "Nation,0,180", "StO,0,-180", "PtChap,90,-180"})
    public void testPersonalizedNode(String name, double la, double lo) {
        PersonalizedNode s = new PersonalizedNode(name, la, lo);
        assertEquals(name, s.getName());
        assertEquals(la, s.getCoordinates().getLatitude());
        assertEquals(lo, s.getCoordinates().getLongitude());
    }

    @ParameterizedTest
    @CsvSource({"S1,1,2"})
    public void testIsInMetro(String name, double la, double lo) {
        PersonalizedNode s = new PersonalizedNode(name, la, lo);
        assertFalse(s.isInMetro());
    }
}
