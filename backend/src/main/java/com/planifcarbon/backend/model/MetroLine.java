package com.planifcarbon.backend.model;

import java.util.*;

public final class MetroLine {
    /** List of metro stations **/
    private final Set<Station> stations;
    /** Schedules for each terminus station in the metro line. */
    private final List<Integer> schedules;
    private final String name;

    /**
     * {@summary Main constructor.}
     * 
     * @param stations stations of this line
     * @param id       id of this line
     */
    public MetroLine(String id, Set<Station> stations, List<Integer> schedules) {
        this.name = id;
        this.stations = Set.copyOf(stations);
        this.schedules = schedules;
    }

    public String getName() { return name; }

    public Set<Station> getStations() { return stations; }

    public List<Integer> getSchedules() {
        return schedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroLine metroLine = (MetroLine) o;
        return Objects.equals(name, metroLine.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "MetroLine{" +
                "id='" + name + '\'' +
                '}';
    }
}
