package com.planifcarbon.backend.model;

/**
 * {@summary Represens the segment between two nodes.}
 */
public abstract class Segment {
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
    protected int distance;

    /**
     * {@summay Main constructor.}
     *
     * @param node1 first point of segment
     * @param node2 second point of segment
     * @param distance distance between two points
     * @param duration travel time from the first point to the second
     */
    public Segment(final Node node1, final Node node2, int distance, double duration) {
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
    public int getDistabce() { return distance; }

    /**
     * {@summary Test if this is equals to anotherSegment.}
     * @param anotherSegment segment to test equals with
     */
    public boolean equals(Segment anotherSegment) {
        // TODO for the future: make sure we really need a deep comparison here
        return (this.id == anotherSegment.id)
                && this.startPoint.equals(anotherSegment.startPoint)
                && this.endPoint.equals(anotherSegment.endPoint)
                && (this.duration == anotherSegment.duration)
                && (this.distance == anotherSegment.distance);
    }
}
