package com.planifcarbon.backend.model;

/**
 * {@summary Represens the segment between two nodes.}
 */
public abstract sealed class Segment permits SegmentMetro, SegmentWalk {
    /** ID counter of class **/
    private static int segmentId = 0;
    /** ID of segment **/
    protected int id = 0;
    /** First point of segment **/
    protected final Node startPoint;
    /** Second point of segment **/
    protected final Node endPoint;
    /** Travel time from the first point to the second **/
    protected double duration;
    /** Distance between two points **/
    protected double distance;

    /**
     * {@summay Main constructor.}
     *
     * @param node1 first point of segment
     * @param node2 second point of segment
     * @param distance distance between two points
     * @param duration travel time from the first point to the second
     */
    public Segment(final Node node1, final Node node2, double distance, double duration) throws IllegalArgumentException {
        if (distance <= 0) {
            throw new IllegalArgumentException("distance must be greater than 0");
        }
        else if (duration <= 0) {
            throw new IllegalArgumentException("duration must be greater than 0");
        }
        
        this.startPoint = node1;
        this.endPoint = node2;
        this.distance = distance;
        this.duration = duration;
        this.id = segmentId++;
    }

    public int getId() { return id; }
    public Node getStartPoint() { return startPoint; }
    public Node getEndPoint() { return endPoint; }
    public double getDuration() { return duration; }
    public double getDistance() { return distance; }

    /**
     * {@summary Test if this is equals to anotherSegment.}
     * @param anotherSegment segment to test equals with
     */
    public boolean equals(Segment anotherSegment) {
        // TODO for the future: make sure if we don't need a deep comparison here
        return (this.id == anotherSegment.id);
    }

    public abstract String toString();
}
