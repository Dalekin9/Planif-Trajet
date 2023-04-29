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

    public MetroDTO(String name) {
        this.name = name;
        this.stations = new ArrayList<NodeDTO>();
        this.schedules = new ArrayList<MetroScheduleDTO>();
    }

    public MetroDTO(String name, List<NodeDTO> stations, List<MetroScheduleDTO> schedules) {
        this.name = name;
        this.stations = stations;
        this.schedules = schedules;
    }

    public List<NodeDTO> getStations() { return stations; }

    public String getName() { return name; }

    public List<MetroScheduleDTO> getSchedules() { return schedules; }
}
