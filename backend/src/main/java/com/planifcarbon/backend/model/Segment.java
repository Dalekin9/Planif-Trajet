package com.planifcarbon.backend.model;

import java.util.Objects;

/**
 * {@summary Represens the segment between two nodes.}
 */
public abstract sealed class Segment permits SegmentMetro, SegmentWalk {
    /** First point of segment **/
    protected final Node startPoint;
    /** Second point of segment **/
    protected final Node endPoint;
    /** Travel time from the first point to the second **/
    protected int duration;
    /** Distance between two points in KM **/
    protected double distance;

    /**
     * {Main constructor.}
     *
     * @param node1    first point of segment
     * @param node2    second point of segment
     * @param distance distance between two points
     * @param duration travel time from the first point to the second
     */
    public Segment(final Node node1, final Node node2, double distance, int duration) throws IllegalArgumentException {
        if (node1 == null) {
            throw new IllegalArgumentException("node1 must not be null");
        } else if (node2 == null) {
            throw new IllegalArgumentException("node2 must not be null");
        } else if (node1.equals(node2)) {
            throw new IllegalArgumentException("node1 and node2 must not be equals");
        } else if (distance < 0) {
            throw new IllegalArgumentException("distance must be greater than 0");
        } else if (duration < 0) {
            throw new IllegalArgumentException("duration must be greater than 0");
        }

        this.startPoint = node1;
        this.endPoint = node2;
        this.distance = distance;
        this.duration = duration;
    }

    /**
     * Returns the starting node of the segment.
     *
     * @return the starting node of the segment
     */
    public Node getStartPoint() { return startPoint; }

    /**
     * Returns the ending node of the segment.
     *
     * @return the ending node of the segment
     */
    public Node getEndPoint() { return endPoint; }

    /**
     * Returns the duration of the segment in seconds.
     *
     * @return the duration of the segment in seconds
     */
    public int getDuration() { return duration; }

    /**
     * Returns the distance of the segment in kilometers.
     *
     * @return the distance of the segment in kilometers
     */
    public double getDistance() { return distance; }


    /**
     * Tests whether this segment is equal to another segment.
     *
     * @param anotherSegment the segment to test equality with
     * @return true if this segment is equal to the given segment, false otherwise
     */
    @Override
    public boolean equals(Object anotherSegment) {
        if (this == anotherSegment)
            return true;
        if (anotherSegment == null || getClass() != anotherSegment.getClass())
            return false;
        Segment segment = (Segment) anotherSegment;
        return Objects.equals(startPoint, segment.startPoint) && Objects.equals(endPoint, segment.endPoint);
    }

    /**
     * Returns a hash code value for the segment.
     *
     * @return a hash code value for the segment
     */
    @Override
    public int hashCode() { return Objects.hash(startPoint, endPoint); }
}
