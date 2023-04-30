package com.planifcarbon.backend.model;

/**
 * { Represents a point on map.}
 */
public class Coordinates {
    private final double latitude;
    private final double longitude;
    private static final double EARTH_RADIUS = 6378.127;

    /**
     * { Main constructor.}
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
    /**
     * Returns the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public double getLatitude() { return latitude; }

    /**
     * Returns the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double getLongitude() { return longitude; }

    /**
     * {Test if this is equals to o.}
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
     * Represents a geographical coordinate point with latitude and longitude.
     *
     * @return A simple string representation of this.
     */
    @Override
    public String toString() { return latitude + ", " + longitude; }

    /**
     * @param co the other coordinate to reach
     *
     * @return distance in km
     */
    public double distanceTo(Coordinates co) {
        double radianLatitudeDistance = Math.toRadians(latitude - co.latitude);
        double radianLongitudeDistance = Math.toRadians(longitude - co.longitude);

        double a = Math.sin(radianLatitudeDistance / 2) * Math.sin(radianLatitudeDistance / 2) + Math.cos(Math.toRadians(co.latitude))
                * Math.cos(Math.toRadians(latitude)) * Math.sin(radianLongitudeDistance / 2) * Math.sin(radianLongitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
