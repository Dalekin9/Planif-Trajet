package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * This class contains unit tests for the {@link PersonalizedNode} class.
 */
public class PersonalizedNodeTest {

    /**
     * Tests the {@link PersonalizedNode} constructor with valid parameters.
     *
     * @param name The name of the personalized node.
     * @param la The latitude of the personalized node.
     * @param lo The longitude of the personalized node.
     */
    @ParameterizedTest
    @CsvSource({"S1,1,2", "S2,0,0", "S3,-1,0", "Nation,0,180", "StO,0,-180", "PtChap,90,-180"})
    public void testPersonalizedNode(String name, double la, double lo) {
        PersonalizedNode s = new PersonalizedNode(name, la, lo);
        assertEquals(name, s.getName());
        assertEquals(la, s.getCoordinates().getLatitude());
        assertEquals(lo, s.getCoordinates().getLongitude());
    }

    /**
     * Tests the {@link PersonalizedNode#isInMetro()} method.
     *
     * @param name The name of the personalized node.
     * @param la The latitude of the personalized node.
     * @param lo The longitude of the personalized node.
     */
    @ParameterizedTest
    @CsvSource({"S1,1,2"})
    public void testIsInMetro(String name, double la, double lo) {
        PersonalizedNode s = new PersonalizedNode(name, la, lo);
        assertFalse(s.isInMetro());
    }
}
