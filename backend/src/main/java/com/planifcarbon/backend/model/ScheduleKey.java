package com.planifcarbon.backend.model;

import java.util.Objects;

public class ScheduleKey {
    private final Station terminusStation;
    private final MetroLine metroLine;

    public ScheduleKey(Station terminusStation, MetroLine metroLine) {
        this.terminusStation = terminusStation;
        this.metroLine = metroLine;
    }

    public Station getTerminusStation() {
        return terminusStation;
    }

    public MetroLine getMetroLine() {
        return metroLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleKey that = (ScheduleKey) o;
        return Objects.equals(terminusStation, that.terminusStation) && Objects.equals(metroLine, that.metroLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terminusStation, metroLine);
    }

    @Override
    public String toString() {
        return "ScheduleKey{" +
                "terminusStation=" + terminusStation +
                ", metroLine=" + metroLine +
                '}';
    }
}
