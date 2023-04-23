package com.planifcarbon.backend.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

public class DataSegmentTest extends Assertions {
    @ParameterizedTest
    @MethodSource("generateDataSegment")
    public void testDataSegment(Node node1, Node node2, int arrivalTime, int departureTime, @Nullable MetroLine line, double distance) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        DataSegment dataSegment2 = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        assertNotNull(dataSegment);
        assertEquals(dataSegment, dataSegment2);
        assertEquals(dataSegment, dataSegment);
    }

    @ParameterizedTest
    @MethodSource("generateDataSegment2")
    public void testDataSegmentToString(Node node1, Node node2, int arrivalTime, int departureTime, @Nullable MetroLine line,
            double distance, String expected) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        System.out.println(dataSegment.toString());
        assertEquals(expected, dataSegment.toString());
    }

    @ParameterizedTest
    @MethodSource("generateDataSegment")
    public void testDataSegmentEquals(Node node1, Node node2, int arrivalTime, int departureTime, @Nullable MetroLine line,
            double distance) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        assertNotNull(dataSegment);
        assertNotEquals(dataSegment, null);
        assertNotEquals(dataSegment, "");
    }

    private static Stream<Arguments> generateDataSegment() {
        return Stream.of(Arguments.of(new PersonalizedNode("A", 0, 0), new PersonalizedNode("B", 1, 2), 0, 1, null, 0.0));
    }
    private static Stream<Arguments> generateDataSegment2() {
        return Stream.of(Arguments.of(new PersonalizedNode("A", 0, 0), new PersonalizedNode("B", 1, 2), 0, 1, null, 0.0,
                "DataSegment{nodeStart=A: 0.0, 0.0, nodeEnd=B: 1.0, 2.0, arrivalTime=0, departureTime=1, line=null, distance=0.0}"));
    }

}
