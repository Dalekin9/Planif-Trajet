package com.planifcarbon.backend.model;

import java.util.Objects;

/**
 * {Represents a point on map.}
 * It have a name and coordinates.
 */
public abstract sealed class Node permits NodeForTest, Station, PersonalizedNode {
    private final String name;
    private final Coordinates coordinates;

    /**
     * {Main constructor.}
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

    /**
     * Returns the name of this node.
     *
     * @return the name of this node
     */
    public String getName() { return name; }

    /**
     * Returns the coordinates of this node.
     *
     * @return the coordinates of this node
     */
    public Coordinates getCoordinates() { return coordinates; }

    /**
     * Calculates and returns the Euclidean distance between this node and the given node.
     *
     * @param node the node to which the distance is to be calculated
     * @return the Euclidean distance between this node and the given node
     */
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

    /**
     * Returns the hash code value for this Node object. The hash code
     * is computed using the name field.
     *
     * @return the hash code value for this Node object
     */
    @Override
    public int hashCode() { return Objects.hash(name); }

    /**
     * Returns a string representation of this Node, which includes its name and coordinates.
     *
     * @return a string representation of this Node
     */
    @Override
    public String toString() { return name + ": " + coordinates; }

    /**
     * Returns whether this node is part of the metro network.
     * This implementation always returns false.
     *
     * @return false
     */
    public abstract boolean isInMetro();
}

// For test only
/**
 * This class extends the Node class and is used for testing purposes only. It represents a node that is not
 * part of the metro network.
 */
final class NodeForTest extends Node {
    /**
     * Creates a new NodeForTest object with the given name, latitude and longitude.
     *
     * @param name      the name of the node
     * @param la  the latitude of the node's coordinates
     * @param lo the longitude of the node's coordinates
     */
    public NodeForTest(String name, double la, double lo) { super(name, la, lo); }

    /**
     * Returns whether this node is part of the metro network.
     * This implementation always returns false.
     *
     * @return false
     */
    @Override
    public boolean isInMetro() { return false; }
}
