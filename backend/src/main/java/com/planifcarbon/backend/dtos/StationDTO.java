package com.planifcarbon.backend.dtos;

import java.util.Objects;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the Stationclass.
 */
@ExcludeFromJacocoGeneratedReport
public class StationDTO {
    private final String name;
    private final double longitude;
    private final double latitude;

    public StationDTO(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() { return name; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StationDTO that = (StationDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        return "StationDTO{" + "name='" + name + '\'' + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
