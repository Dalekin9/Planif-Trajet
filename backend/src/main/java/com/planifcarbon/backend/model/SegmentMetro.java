package com.planifcarbon.backend.model;

/**
 * {@summary Represens the segment between two stations of metro.}
 */
public final class SegmentMetro extends Segment {

    /** Name of the line to which the segment belongs **/
    private final String line;

    /**
     * {@summay Main constructor.}
     *
     * @param node1    first point of segment
     * @param node2    second point of segment
     * @param distance distance between two points
     * @param duration travel time from the first point to the second
     * @param line     name of the line to which the segment belongs
     */
    public SegmentMetro(Node node1, Node node2, double distance, double duration, String line) {
        super(node1, node2, distance, duration);
        this.line = line;
    }

    /**
     * {@summary Gets name of metro line.}
     * @return the line
     */
    public String getLine() { return line; }

    /**
     * {@return A string representation of SegmentMetro.}
     */
    public String toString() {
        return String.format("metroSegm %d: line %s, %d s, %d m\n", id, line, duration, distance);
    }
}
