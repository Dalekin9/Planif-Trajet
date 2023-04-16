package com.planifcarbon.backend.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

/**
 * {@summary Represents a metro station.}
 */
public final class Station extends Node {
    /** Time to train movement from the each terminal station and this one. */
    private final Map<ScheduleKey, Integer> schedules;
    /** Time table of all arrivings trains to this station. */
    private Map<ScheduleKey, List<Integer>> timeTable;

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
        this.timeTable = new HashMap<ScheduleKey, List<Integer>>();
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

    public Map<ScheduleKey, List<Integer>> getTimeTable() { return timeTable;}

    public void addTimeToTimeTable(ScheduleKey key,  List<Integer> times) { this.timeTable.put(key, times); }

}
