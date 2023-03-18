package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class StationTerminusTest {
    @ParameterizedTest
    @MethodSource("generateData")
    public void testStation(String name, double la, double lo, List<Long> schedules) {
        StationTerminus s = new StationTerminus(name, la, lo, schedules);
        assertEquals(name, s.getName());
        assertEquals(la, s.getCoordinates().getLatitude());
        assertEquals(lo, s.getCoordinates().getLongitude());
        // TODO test that schedules are the same
    }

    @ParameterizedTest
    @MethodSource("generateData")
    public void testIsInMetro(String name, double la, double lo, List<Long> schedules) {
        StationTerminus s = new StationTerminus(name, la, lo, schedules);
        assertTrue(s.isInMetro());
    }
    @ParameterizedTest
    @CsvSource({"S1,1,2"})
    public void testBadStation(String name, double la, double lo) {
        assertThrows(IllegalArgumentException.class, () -> {
            new StationTerminus(name, la, lo, null);
        });
    }

    static Stream<Arguments> generateData() { return Stream.of(Arguments.of("ST1", 1, 2, List.of(1l, 2l))); }
}
