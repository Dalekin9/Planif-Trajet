package com.planifcarbon.backend.model;

/**
 * {Represents a segment walked by a pedestrian with average walking speed between two points.}
 */
public final class SegmentWalk extends Segment {

    /*
     * Reference Information: Average walking speed by age:
     * 20-29 years: 4.83 km/h
     * 30-39 years: 4.54 km/h
     * 40-49 years: 4.54 km/h
     * 50-59 years: 4.43 km/h
     * > 60 years: 4.36 km/h
     * Sourse: https://blog.mypacer.com/what-your-walking-speed-says-about-your-health-and-how-long-youll-live/
     */

    /** Average walking speed (km/h) **/
    private static final double SPEED = 4.4;
    private static final int HTOS = 3600; // 60 * 60

    /**
     * {Main constructor.}
     * Duration will be calculated based on distance and the average walking speed.
     *
     * @param node1    first point of walking segment
     * @param node2    second point of walking segment
     * @param distance distance between two points
     */
    public SegmentWalk(Node node1, Node node2, double distance) { super(node1, node2, distance, (int) (distance * HTOS / SPEED)); }

    /**
     * {Main constructor.}
     * Distance will be calculated based on coordinates of two points.
     * Duration will be calculated based on distance and the average walking speed.
     *
     * @param node1 first point of walking segment
     * @param node2 second point of walking segment
     */
    public SegmentWalk(Node node1, Node node2) { this(node1, node2, node1.distanceTo(node2)); }

    /**
     * Function for tests only.
     *
     * @return SPEED constant wich represent sverage walking speed (km/h).
     */
    public static double getSpeed() { return SPEED; }
}
