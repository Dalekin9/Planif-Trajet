package com.planifcarbon.backend.dtos;

import java.util.Collections;
import java.util.List;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the MetroSchedule class.
 */
@ExcludeFromJacocoGeneratedReport
public class MetroScheduleDTO {
    private final String line;
    private final String station;
    private final List<Integer> schedules;

    /**
     * Creates a new MetroScheduleDTO object.
     *
     * @param metroName The name of the metro line.
     * @param terminus The name of the terminus station.
     * @param schedules The arrival times of the metro train at the station.
     */
    public MetroScheduleDTO(String metroName, String terminus, List<Integer> schedules) {
        this.line = metroName;
        this.station = terminus;
        this.schedules = schedules;
        Collections.sort(this.schedules);
    }

    /**
     * Returns the arrival times of the metro train at the station.
     *
     * @return The arrival times of the metro train at the station.
     */
    public List<Integer> getSchedules() { return schedules; }

    /**
     * Returns the name of the metro line.
     *
     * @return The name of the metro line.
     */
    public String getLine() { return line; }

    /**
     * Returns the name of the terminus station.
     *
     * @return The name of the terminus station.
     */
    public String getTerminus() { return station; }
}
