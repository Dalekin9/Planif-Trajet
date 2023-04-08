package com.planifcarbon.backend.model;

import java.util.HashMap;
import java.util.Map;

/**
 * {@summary Represents a metro station.}
 */
public final class Station extends Node {

    private final Map<ScheduleKey, Integer> schedules;

    /**
     * {@summary Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     */
    public Station(final String name, final double latitude, final double longitude) {
        super(name, latitude, longitude);
        this.schedules = new HashMap<ScheduleKey, Integer>();
    }
    @Override
    public boolean isInMetro() { return true; }

    public void addSchedule(ScheduleKey key, int duration) {
        this.schedules.putIfAbsent(key, duration);
    }

    public int getScheduleForKey(ScheduleKey key) {
        return this.schedules.getOrDefault(key, 0);
    }

    public Map<ScheduleKey, Integer> getSchedules() {
        return schedules;
    }

}
