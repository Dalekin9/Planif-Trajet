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
     * {@summay Main constructor.}
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

    public Node getStartPoint() { return startPoint; }
    public Node getEndPoint() { return endPoint; }
    public int getDuration() { return duration; }
    public double getDistance() { return distance; }


    /**
     * {@summary Test if this is equals to anotherSegment.}
     * 
     * @param anotherSegment segment to test equals with
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

    @Override
    public int hashCode() { return Objects.hash(startPoint, endPoint); }
}
