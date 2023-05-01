package com.planifcarbon.backend.dtos;

import java.util.Objects;
import java.util.Set;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the Stationclass.
 */
@ExcludeFromJacocoGeneratedReport
public class StationCorrespondence {
    private final NodeDTO station;
    private final Set<String> metroLines;

    /**
     * Creates a new StationCorrespondence instance.
     *
     * @param station The station in the correspondence.
     * @param metroLines The set of metro lines that serve the station.
     */
    public StationCorrespondence(NodeDTO station, Set<String> metroLines) {
        this.station = station;
        this.metroLines = metroLines;
    }

    /**
     * Returns the set of metro lines that serve the station in this correspondence.
     *
     * @return The set of metro lines that serve the station.
     */
    public Set<String> getMetroLines() { return metroLines; }

    /**
     * Returns the station in this correspondence.
     *
     * @return The station in this correspondence.
     */
    public NodeDTO getStation() { return station; }

    /**
     * Returns the number of metro lines that serve the station in this correspondence.
     *
     * @return The number of metro lines that serve the station.
     */
    public int getNbStations() { return this.metroLines.size(); }

    /**
     * Compares this StationCorrespondence instance to the specified object for equality.
     *
     * @param o The object to compare to this StationCorrespondence instance.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StationCorrespondence that = (StationCorrespondence) o;
        return Objects.equals(station, that.station);
    }

    /**
     * Returns the hash code value for this StationCorrespondence instance.
     *
     * @return The hash code value for this StationCorrespondence instance.
     */
    @Override
    public int hashCode() { return Objects.hash(station); }

    /**
     * Returns a string representation of this StationCorrespondence instance.
     *
     * @return A string representation of this StationCorrespondence instance.
     */
    @Override
    public String toString() { return "StationCorrespondence{" + "station=" + station + ", metroLines=" + metroLines + '}'; }
}
