package com.planifcarbon.backend.model;

/**
 * {@summary Represents a metro station.}
 */
public sealed class Station extends Node permits StationTerminus {
    /**
     * {@summary Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     */
    public Station(final String name, final double latitude, final double longitude) { super(name, latitude, longitude); }
    @Override
    public boolean isInMetro() { return true; }
}
