package com.planifcarbon.backend.model;

import java.util.*;

/**
 * {@summary Represents a metro line.}
 */
public final class MetroLine {
    /** List of metro stations **/
    private final Set<Station> stations;
    /** Schedules for each terminus station in the metro line. */
    private final List<Integer> schedules;
    /** Name of metro line **/
    private final String name;
    /** Terminus station of Metro Line **/
    private final Station terminusStation;

    /**
     * {@summary Main constructor.}
     * 
     * @param stations stations of this line
     * @param id       id of this line
     */
    public MetroLine(String id, Set<Station> stations, List<Integer> schedules, Station terminus) {
        this.name = id;
        this.stations = Set.copyOf(stations);
        this.schedules = schedules;
        Collections.sort(this.schedules);
        this.terminusStation = terminus;
    }

    public String getName() { return name; }

    public String getNonVariantName() { return this.name.split(" ")[0]; }

    public Set<Station> getStations() { return stations; }

    public List<Integer> getSchedules() { return schedules; }

    public Station getTerminusStation() { return terminusStation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroLine metroLine = (MetroLine) o;
        return Objects.equals(name, metroLine.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() {
        return "MetroLine{" +
                "id='" + name + '\'' +
                '}';
    }
}
