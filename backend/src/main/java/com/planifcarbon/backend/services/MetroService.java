package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.MetroDTO;
import com.planifcarbon.backend.dtos.MetroScheduleDTO;
import com.planifcarbon.backend.dtos.StationDTO;
import com.planifcarbon.backend.model.MetroLine;
import com.planifcarbon.backend.model.MetroMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MetroService {
    private final MetroMap metroMap;

    public MetroService(MetroMap metroMap) {
        this.metroMap = metroMap;
    }

    public List<MetroDTO> getMetros() {
        return this.metroMap.getLines()
                .values()
                .stream()
                .map(MetroLine::getNonVariantName)
                .distinct()
                .map(MetroDTO::new)
                .collect(Collectors.toList());
    }

    public MetroDTO getMetroByName(String metroName) {
        List<MetroLine> metroLinesVariant = this.metroMap.getLines()
                .values()
                .stream()
                .filter(metroLine -> metroLine.getNonVariantName().equals(metroName))
                .collect(Collectors.toList());
        Set<StationDTO> stations = metroLinesVariant.stream().flatMap(metroLine -> metroLine.getStations().stream())
                .map(station -> new StationDTO(station.getName(), station.getCoordinates().getLatitude(),
                        station.getCoordinates().getLongitude()))
                .collect(Collectors.toSet());
        List<MetroScheduleDTO> schedules = metroLinesVariant.stream().map(metroLine -> new MetroScheduleDTO(metroName, metroLine.getTerminusStation().getName(), metroLine.getSchedules())).collect(Collectors.toList());

        return new MetroDTO(metroName, stations, schedules);
    }
}
