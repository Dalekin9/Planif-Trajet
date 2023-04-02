package com.planifcarbon.backend.model;

import java.util.Objects;

/**
 * {@summary Represents a point on map.}
 */
public abstract sealed class Node permits NodeForTest, Station, PersonalizedNode {
    private final String name;
    private final Coordinates coordinates;
    // private int type; // used by Dijkstra ?
    // TODO store current used name for created node to avoid duplicate name

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
    public double distanceTo(Node node) { return coordinates.distanceTo(node.coordinates); }

    /**
     * {@summary Test if this is equals to o.}
     * Each Node have a unique name, so two nodes are equals if they have the same name.
     * 
     * @param o object to test equals with
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

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
