package com.planifcarbon.backend.dtos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the Metro class.
 */
@ExcludeFromJacocoGeneratedReport
public class MetroDTO {
    private final String name;
    private final List<NodeDTO> stations;
    private final List<MetroScheduleDTO> schedules;

    /**
     * Constructor for a MetroDTO object with only the metro name provided.
     * Initializes the stations and schedules lists as empty ArrayLists.
     *
     * @param name The name of the metro.
     */
    public MetroDTO(String name) {
        this.name = name;
        this.stations = new ArrayList<NodeDTO>();
        this.schedules = new ArrayList<MetroScheduleDTO>();
    }

    /**
     * Constructor for a MetroDTO object with the metro name, stations, and schedules provided.
     *
     * @param name The name of the metro.
     * @param stations The list of NodeDTO objects representing the stations in the metro.
     * @param schedules The list of MetroScheduleDTO objects representing the schedules for the metro.
     */
    public MetroDTO(String name, List<NodeDTO> stations, List<MetroScheduleDTO> schedules) {
        this.name = name;
        this.stations = stations;
        this.schedules = schedules;
    }

    /**
     * Returns the list of NodeDTO objects representing the stations in the metro.
     *
     * @return The list of stations in the metro.
     */
    public List<NodeDTO> getStations() { return stations; }

    /**
     * Returns the name of the metro.
     *
     * @return The name of the metro.
     */
    public String getName() { return name; }

    /**
     * Returns the list of MetroScheduleDTO objects representing the schedules for the metro.
     *
     * @return The list of schedules for the metro.
     */
    public List<MetroScheduleDTO> getSchedules() { return schedules; }
}
