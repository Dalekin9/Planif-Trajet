package com.planifcarbon.backend.model;

import java.util.Objects;

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
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("line must not be null or blank");
        this.line = line;
    }

    /**
     * {@summary Gets name of metro line.}
     * 
     * @return the line
     */
    public String getLine() { return line; }


    /**
     * {@return A string representation of SegmentMetro.}
     */
    @Override
    public String toString() {
        return "SegmentMetro{" +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                ", duration=" + duration +
                ", distance=" + distance +
                ", line=" + line +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SegmentMetro that = (SegmentMetro) o;
        return super.equals(that) && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), line);
    }
}
