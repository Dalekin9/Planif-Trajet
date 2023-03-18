package com.planifcarbon.backend.model;

/**
 * {@summary Represents a point on map.}
 */
public abstract sealed class Node permits NodeForTest, Station, PersonalizedNode {
    private final String name;
    private final Coordinates coordinates;
    // private int type; // used by Dijkstra ?

    /**
     * {@summay Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     */
    public Node(final String name, final double latitude, final double longitude) {
        if (name == null)
            throw new IllegalArgumentException("name must not be null");
        this.name = name;
        this.coordinates = new Coordinates(latitude, longitude);
    }

    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }

    /**
     * {@summary Test if this is equals to o.}
     * Each node is unique, so it is equals to itself only.
     * 
     * @param o object to test equals with
     */
    @Override
    public boolean equals(Object o) {
        return this == o;
        // // @formatter:off
        // return o != null
        //         && o instanceof Node
        //         && ((Node) o).name.equals(name)
        //         && ((Node) o).coordinates.equals(coordinates);
        // // @formatter:on
    }

    /**
     * {@return A simple string representation of this.}
     */
    @Override
    public String toString() { return name + ": " + coordinates; }

    public abstract boolean isInMetro();
}

// For test only
final class NodeForTest extends Node {
    public NodeForTest(String name, double la, double lo) { super(name, la, lo); }
    @Override
    public boolean isInMetro() { return false; }
}