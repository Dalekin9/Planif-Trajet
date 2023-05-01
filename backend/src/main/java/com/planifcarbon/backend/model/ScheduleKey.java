package com.planifcarbon.backend.model;

import java.util.Objects;

/**
 * Tuple of station and metro line that uniquely identifies a schedule.
 */
public class ScheduleKey {
    private final Station terminusStation;
    private final MetroLine metroLine;

    /**
     * Main Constructor.
     *
     * @param terminusStation metro line terminus station.
     * @param metroLine metro line.
     */
    public ScheduleKey(Station terminusStation, MetroLine metroLine) {
        this.terminusStation = terminusStation;
        this.metroLine = metroLine;
    }

    /**
     * Gets the terminus station of the schedule.
     *
     * @return the terminus station
     */
    public Station getTerminusStation() { return terminusStation; }

    /**
     * Gets the metro line of the schedule.
     *
     * @return the metro line
     */
    public MetroLine getMetroLine() { return metroLine; }

    /**
     * Compares this ScheduleKey with another object for equality.
     * Two ScheduleKeys are considered equal if their terminus station and metro line are equal.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ScheduleKey that = (ScheduleKey) o;
        return Objects.equals(terminusStation, that.terminusStation) && Objects.equals(metroLine, that.metroLine);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() { return Objects.hash(terminusStation, metroLine); }

    /**
     * Returns a string representation of the ScheduleKey.
     *
     * @return a string representation of the ScheduleKey
     */
    @Override
    public String toString() {
        return "terminusStation = " + terminusStation +
                ", metroLine = " + metroLine;
    }
}
