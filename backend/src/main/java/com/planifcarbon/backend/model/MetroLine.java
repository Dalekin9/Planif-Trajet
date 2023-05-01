package com.planifcarbon.backend.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
     * Constructs a new MetroLine object.
     *
     * @param name the name of the metro line
     * @param stations the set of stations on the metro line
     * @param schedules the list of schedules for the metro line
     * @param terminusStation the terminus station for the metro line
     */
    public MetroLine(String name, Set<Station> stations, List<Integer> schedules, Station terminusStation) {
        this.name = name;
        this.stations = Set.copyOf(stations);
        this.schedules = schedules;
        Collections.sort(this.schedules);
        this.terminusStation = terminusStation;
    }

    /**
     * Returns the name of the metro line.
     *
     * @return the name of the metro line
     */
    public String getName() { return name; }

    /**
     * Returns the non-variant name of the metro line.
     *
     * @return the non-variant name of the metro line
     */
    public String getNonVariantName() { return this.name.split(" ")[0]; }

    /**
     * Returns the set of stations on the metro line.
     *
     * @return the set of stations on the metro line
     */
    public Set<Station> getStations() { return stations; }

    /**
     * Returns the list of schedules for the metro line.
     *
     * @return the list of schedules for the metro line
     */
    public List<Integer> getSchedules() { return schedules; }

    /**
     * Returns the terminus station for the metro line.
     *
     * @return the terminus station for the metro line
     */
    public Station getTerminusStation() { return terminusStation; }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the object with which to compare
     * @return true if this object is the same as the o argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetroLine metroLine = (MetroLine) o;
        return Objects.equals(name, metroLine.name);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() { return Objects.hash(name); }

    /**
     * Returns a string representation of this MetroLine object.
     * The string representation consists of the class name, the ID of the line enclosed in curly braces.
     *
     * @return a string representation of this MetroLine object
     */
    @Override
    public String toString() { return "MetroLine{" + "id='" + name + '\'' + '}'; }
}
