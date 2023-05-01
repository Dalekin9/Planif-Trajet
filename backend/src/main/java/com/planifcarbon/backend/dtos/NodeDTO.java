package com.planifcarbon.backend.dtos;

import java.util.Objects;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the Stationclass.
 */
@ExcludeFromJacocoGeneratedReport
public class NodeDTO {
    private final String name;
    private final double longitude;
    private final double latitude;

    /**
     * Constructs a new NodeDTO object with the given name, longitude, and latitude.
     *
     * @param name The name of the station represented by this node.
     * @param longitude The longitude of the station represented by this node.
     * @param latitude The latitude of the station represented by this node.
     */
    public NodeDTO(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Gets the name of the station represented by this node.
     *
     * @return The name of the station represented by this node.
     */
    public String getName() { return name; }

    /**
     * Gets the latitude of the station represented by this node.
     *
     * @return The latitude of the station represented by this node.
     */
    public double getLatitude() { return latitude; }

    /**
     * Gets the longitude of the station represented by this node.
     *
     * @return The longitude of the station represented by this node.
     */
    public double getLongitude() { return longitude; }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare to this one.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        NodeDTO that = (NodeDTO) o;
        return name.equalsIgnoreCase(that.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() { return Objects.hash(name); }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "StationDTO{" + "name='" + name + '\'' + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
