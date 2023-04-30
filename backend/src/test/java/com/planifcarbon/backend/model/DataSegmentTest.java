package com.planifcarbon.backend.model;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;


/**
 *  This class contains JUnit tests for the {@link DataSegment} class.
 */
public class DataSegmentTest extends Assertions {

    /**
     * Test the {@link DataSegment#DataSegment(Node, Node, double, double, MetroLine, double)} constructor and the
     *
     * {@link DataSegment#equals(Object)} method.
     * @param node1 the start node of the segment
     * @param node2 the end node of the segment
     * @param arrivalTime the time at which a metro train arrives at the start node
     * @param departureTime the time at which a metro train departs from the start node
     * @param line the metro line that this segment belongs to
     * @param distance the distance between the start and end nodes
     */
    @ParameterizedTest
    @MethodSource("generateDataSegment")
    public void testDataSegment(Node node1, Node node2, int arrivalTime, int departureTime, @Nullable MetroLine line, double distance) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        DataSegment dataSegment2 = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        assertNotNull(dataSegment);
        assertEquals(dataSegment, dataSegment2);
        assertEquals(dataSegment, dataSegment);
    }

    /**
     * Test the {@link DataSegment#toString()} method.
     *
     * @param node1 the start node of the segment
     * @param node2 the end node of the segment
     * @param arrivalTime the time at which a metro train arrives at the start node
     * @param departureTime the time at which a metro train departs from the start node
     * @param line the metro line that this segment belongs to
     * @param distance the distance between the start and end nodes
     * @param expected the expected string representation of the data segment
     */
    @ParameterizedTest
    @MethodSource("generateDataSegment2")
    public void testDataSegmentToString(Node node1, Node node2, double arrivalTime, double departureTime, @Nullable MetroLine line,
            double distance, String expected) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        assertEquals(expected, dataSegment.toString());
    }

    /**
     * Test the {@link DataSegment#equals(Object)} method for invalid input.
     *
     * @param node1 the start node of the segment
     * @param node2 the end node of the segment
     * @param arrivalTime the time at which a metro train arrives at the start node
     * @param departureTime the time at which a metro train departs from the start node
     * @param line the metro line that this segment belongs to
     * @param distance the distance between the start and end nodes
     */
    @ParameterizedTest
    @MethodSource("generateDataSegment")
    public void testDataSegmentEquals(Node node1, Node node2, int arrivalTime, int departureTime, @Nullable MetroLine line,
            double distance) {
        DataSegment dataSegment = new DataSegment(node1, node2, arrivalTime, departureTime, line, distance);
        assertNotNull(dataSegment);
        assertNotEquals(dataSegment, null);
        assertNotEquals(dataSegment, "");
    }

    /**
     * Generate a stream of arguments for {@link #testDataSegment(Node, Node, int, int, MetroLine, double)}.
     *
     * @return a stream of arguments
     */
    private static Stream<Arguments> generateDataSegment() {
        return Stream.of(Arguments.of(new PersonalizedNode("A", 0, 0), new PersonalizedNode("B", 1, 2), 0, 1, null, 0.0));
    }

    /**
     * Generate a stream of arguments for {@link #testDataSegmentToString(Node, Node, double, double, MetroLine, double, String)}.
     *
     * @return a stream of arguments
     */
    private static Stream<Arguments> generateDataSegment2() {
        return Stream.of(Arguments.of(new PersonalizedNode("A", 0, 0), new PersonalizedNode("B", 1, 2), 0, 1, null, 0.0,
                "DataSegment{nodeStart=A: 0.0, 0.0, nodeEnd=B: 1.0, 2.0, arrivalTime=0.0, departureTime=1.0, line=null, distance=0.0}"));
    }
}
