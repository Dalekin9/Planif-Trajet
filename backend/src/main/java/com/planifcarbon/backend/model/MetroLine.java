package com.planifcarbon.backend.model;

import java.util.Collection;
import java.util.Set;

public final class MetroLine {
    private Set<Station> stations;
    private String id;

    /**
     * {@summary Main constructor.}
     * 
     * @param stations stations of this line
     * @param id       id of this line
     */
    public MetroLine(Collection<Station> stations, String id) {
        this.id = id;
        this.stations = Set.copyOf(stations);
    }

    public String getId() { return id; }
    public Set<Station> getStations() { return stations; }

}
