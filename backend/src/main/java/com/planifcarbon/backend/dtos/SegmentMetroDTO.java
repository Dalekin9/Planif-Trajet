package com.planifcarbon.backend.dtos;

import java.util.Objects;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the SegmentMetro class.
 */
@ExcludeFromJacocoGeneratedReport
public class SegmentMetroDTO {
    private final NodeDTO start;
    private final NodeDTO end;
    private final int duration;
    private final double distance;
    private final String line;

    /**
     * Creates a new SegmentMetroDTO object with the given start and end stations, duration, distance and metro line.
     *
     * @param start the starting station of the segment
     * @param end the ending station of the segment
     * @param duration the duration of the segment in minutes
     * @param distance the distance of the segment in kilometers
     * @param line the name of the metro line that the segment belongs to
     */
    public SegmentMetroDTO(NodeDTO start, NodeDTO end, int duration, double distance, String line) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.distance = distance;
        this.line = line;
    }

    /**
     * Checks if this SegmentMetroDTO object is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SegmentMetroDTO that = (SegmentMetroDTO) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(line, that.line);
    }

    /**
     * Computes the hash code of this SegmentMetroDTO object.
     *
     * @return the hash code of this object
     */
    @Override
    public int hashCode() { return Objects.hash(start, end, line); }

    /**
     * Returns the ending station of this segment.
     *
     * @return the ending station
     */
    public NodeDTO getEnd() { return end; }

    /**
     * Returns the starting station of this segment.
     *
     * @return the starting station
     */
    public NodeDTO getStart() { return start; }

    /**
     * Returns the duration of this segment in minutes.
     *
     * @return the duration in minutes
     */
    public int getDuration() { return duration; }

    /**
     * Returns the distance of this segment in kilometers.
     *
     * @return the distance in kilometers
     */
    public double getDistance() { return distance; }

    /**
     * Returns the name of the metro line that this segment belongs to.
     *
     * @return the name of the metro line
     */
    public String getLine() { return line; }
}
