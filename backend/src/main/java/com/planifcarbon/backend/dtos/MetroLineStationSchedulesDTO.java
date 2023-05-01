package com.planifcarbon.backend.dtos;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

import java.util.List;


/**
 * The MetroLineStationSchedulesDTO class represents the schedule for a specific station in a specific metro line.
 * It contains information about the metro line, the station, and a list of scheduled times for that station.
 */
@ExcludeFromJacocoGeneratedReport
public class MetroLineStationSchedulesDTO {
    /**
     * The name of the metro line.
     */
    private final String metroLine;
    /**
     * The name of the station.
     */
    private final String station;
    /**
     * A list of scheduled times for the station.
     */
    private final List<Integer> schedules;

    /**
     * Constructs a MetroLineStationSchedulesDTO object with the specified metro line, station, and schedules.
     *
     * @param metroLine the name of the metro line
     * @param station   the name of the station
     * @param schedules a list of scheduled times for the station
     */
    public MetroLineStationSchedulesDTO(String metroLine, String station, List<Integer> schedules) {
        this.metroLine = metroLine;
        this.station = station;
        this.schedules = schedules;
    }

    /**
     * Returns the name of the metro line.
     *
     * @return the name of the metro line
     */
    public String getMetroLine() {
        return metroLine;
    }

    /**
     * Returns the list of scheduled times for the station.
     *
     * @return a list of scheduled times for the station
     */
    public List<Integer> getSchedules() {
        return schedules;
    }

    /**
     * Returns the name of the station.
     *
     * @return the name of the station
     */
    public String getStation() {
        return station;
    }
}
