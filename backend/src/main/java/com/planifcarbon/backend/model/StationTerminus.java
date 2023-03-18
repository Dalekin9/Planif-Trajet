package com.planifcarbon.backend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@summary Represents a metro station.}
 */
public final class StationTerminus extends Station {
    private Set<Long> schedules;
    /**
     * {@summary Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     * @param schedules list of start train on this station
     */
    public StationTerminus(final String name, final double latitude, final double longitude, Collection<Long> schedules) {
        super(name, latitude, longitude);
        if (schedules == null)
            throw new IllegalArgumentException("schedules must not be null");
        this.schedules = new HashSet<Long>();
        for (Long time : schedules) {
            this.schedules.add(time);
        }
    }
}
