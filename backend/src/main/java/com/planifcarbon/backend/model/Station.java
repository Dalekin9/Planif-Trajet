package com.planifcarbon.backend.model;

import java.util.HashMap;
import java.util.Map;

/**
 * {Represents a metro station.}
 */
public final class Station extends Node {
    /** Time to train movement from the each terminal station and this one. */
    private final Map<ScheduleKey, Integer> schedules;

    /**
     * {Main constructor.}
     * 
     * @param name      name of this
     * @param latitude  latitude of coordinates of this
     * @param longitude longitude of coordinates of this
     */
    public Station(final String name, final double latitude, final double longitude) {
        super(name, latitude, longitude);
        this.schedules = new HashMap<>();
    }

    /**
     * Returns whether the station is part of the metro network.
     *
     * @return true if the station is part of the metro network, false otherwise
     */
    @Override
    public boolean isInMetro() { return true; }

    /**
     * Adds a schedule for the given line and terminus station.
     *
     * @param key      the schedule key (line and terminus station)
     * @param duration the duration in minutes of the trip to the terminus station
     */
    public void addSchedule(ScheduleKey key, int duration) {
        this.schedules.putIfAbsent(key, duration);
    }

    /**
     * Gets the duration in minutes of the trip to the terminus station for the given schedule key.
     *
     * @param key the schedule key (line and terminus station)
     * @return the duration in minutes of the trip to the terminus station for the given schedule key
     */
    public int getScheduleForKey(ScheduleKey key) {
        return this.schedules.getOrDefault(key, 0);
    }

    /**
     * Gets a map of all schedules for this station.
     *
     * @return a map of all schedules for this station
     */
    public Map<ScheduleKey, Integer> getSchedules() {
        return schedules;
    }
}
