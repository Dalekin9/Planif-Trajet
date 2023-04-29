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

    public StationCorrespondence(NodeDTO station, Set<String> metroLines) {
        this.station = station;
        this.metroLines = metroLines;
    }

    public Set<String> getMetroLines() { return metroLines; }

    public NodeDTO getStation() { return station; }

    public int getNbStations() { return this.metroLines.size(); }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StationCorrespondence that = (StationCorrespondence) o;
        return Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() { return Objects.hash(station); }

    @Override
    public String toString() { return "StationCorrespondence{" + "station=" + station + ", metroLines=" + metroLines + '}'; }
}
