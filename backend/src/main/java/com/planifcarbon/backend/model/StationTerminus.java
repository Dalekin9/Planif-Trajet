package com.planifcarbon.backend.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * {@summary Represents a metro station.}
 */
public final class StationTerminus extends Station {
    /** Sorted list of time where a new train leave the station (in ms after 0:00). */
    private List<Integer> schedules;
    /**
     * {@summary Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     * @param schedules list of start train on this station
     */
    public StationTerminus(final String name, final double latitude, final double longitude, Collection<Integer> schedules) {
        super(name, latitude, longitude);
        if (schedules == null)
            throw new IllegalArgumentException("schedules must not be null");
        this.schedules = new ArrayList<Integer>();
        for (Integer time : schedules) {
            this.schedules.add(time);
        }
        Collections.sort(this.schedules);
    }
}
