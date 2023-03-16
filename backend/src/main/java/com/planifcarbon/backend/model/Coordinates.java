package com.planifcarbon.backend.model;

/**
 * {@summary Represents a point on map.}
 */
public class Coordinates {
    private final double latitude;
    private final double longitude;

    /**
     * {@summay Main constructor.}
     * 
     * @param latitude  latitude of this
     * @param longitude longitude of this
     */
    public Coordinates(final double latitude, final double longitude) {
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("latitude must be between -90 and 90");
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("longitude must be between -180 and 180");
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    /**
     * {@summary Test if this is equals to o.}
     * 
     * @param o object to test equals with
     */
    @Override
    public boolean equals(Object o) {
        // @formatter:off
        return o != null
                && o instanceof Coordinates
                && ((Coordinates) o).latitude == latitude
                && ((Coordinates) o).longitude == longitude;
        // @formatter:on
    }


    /**
     * {@return A simple string representation of this.}
     */
    @Override
    public String toString() { return latitude + ", " + longitude; }

    public double distanceTo(Coordinates co) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
