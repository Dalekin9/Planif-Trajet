package com.planifcarbon.backend.dtos;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

import java.util.Objects;
import java.util.Set;

@ExcludeFromJacocoGeneratedReport
public class StationCorrespondence {
    private final StationDTO station;
    private final Set<String> metroLines;

    public StationCorrespondence(StationDTO station, Set<String> metroLines) {
        this.station = station;
        this.metroLines = metroLines;
    }

    public Set<String> getMetroLines() {
        return metroLines;
    }

    public StationDTO getStation() {
        return station;
    }

    public int getNbStations() {
        return this.metroLines.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationCorrespondence that = (StationCorrespondence) o;
        return Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station);
    }

    @Override
    public String toString() {
        return "StationCorrespondence{" +
                "station=" + station +
                ", metroLines=" + metroLines +
                '}';
    }
}
