package com.planifcarbon.backend.model;

/**
 * {Represents a user point on map.}
 *
 * It have a name and coordinates.
 * It can be used as a start or end point of a path.
 */
public final class PersonalizedNode extends Node {
    /**
     * {Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     */
    public PersonalizedNode(final String name, final double latitude, final double longitude) { super(name, latitude, longitude); }

    /**
     * Returns whether this node belongs to the metro network or not.
     *
     * @return true if this node belongs to the metro network, false otherwise.
     */
    @Override
    public boolean isInMetro() { return false; }
}
