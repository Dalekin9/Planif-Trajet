package com.planifcarbon.backend.model;

import java.util.Objects;

/**
 * {@summary Represens the segment between two stations of metro.}
 */
public final class SegmentMetro extends Segment {

    /** Name of the line to which the segment belongs **/
    private final String line;

    /**
     * {Main constructor.}
     *
     * @param node1    first point of segment
     * @param node2    second point of segment
     * @param distance distance between two points
     * @param duration travel time from the first point to the second
     * @param line     name of the line to which the segment belongs
     */
    public SegmentMetro(Node node1, Node node2, double distance, int duration, String line) {
        super(node1, node2, distance, duration);
        if (line == null || line.isBlank())
            throw new IllegalArgumentException("line must not be null or blank");
        this.line = line;
    }

    /**
     * {Gets name of metro line.}
     * 
     * @return the line
     */
    public String getLine() { return line; }


    /**
     * Returns a string representation of the SegmentMetro object.
     *
     * @return a string representation of the SegmentMetro object
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

    /**
     * Checks whether this SegmentMetro object is equal to another object.
     *
     * @param o the object to compare this SegmentMetro object against
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         if (!super.equals(o)) return false;
         SegmentMetro that = (SegmentMetro) o;
         return Objects.equals(line, that.line);
    }

    /**
     * Returns the hash code value for this SegmentMetro object.
     *
     * @return the hash code value for this SegmentMetro object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), line);
    }
}
