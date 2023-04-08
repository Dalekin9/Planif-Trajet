package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.MetroDTO;
import com.planifcarbon.backend.dtos.MetroScheduleDTO;
import com.planifcarbon.backend.dtos.StationCorrespondence;
import com.planifcarbon.backend.dtos.StationDTO;
import com.planifcarbon.backend.model.MetroLine;
import com.planifcarbon.backend.model.MetroMap;
import com.planifcarbon.backend.model.Station;
import org.springframework.stereotype.Service;

import java.util.*;
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
                .map(this::stationToStationDTO)
                .collect(Collectors.toSet());
        List<MetroScheduleDTO> schedules = metroLinesVariant.stream().map(metroLine -> new MetroScheduleDTO(metroName
                , metroLine.getTerminusStation().getName(), metroLine.getSchedules())).collect(Collectors.toList());

        return new MetroDTO(metroName, stations, schedules);
    }

    public List<StationCorrespondence> getAllStationsCorrespondence() {
        Map<StationDTO, Set<String>> stationCorrespondence = new HashMap<>();
        List<MetroLine> metroLines = new ArrayList<>(this.metroMap.getLines().values());
        for (MetroLine line : metroLines) {
            line.getStations().stream().map(this::stationToStationDTO).forEach(stationDTO -> {
                if (stationCorrespondence.containsKey(stationDTO)) {
                    stationCorrespondence.get(stationDTO).add(line.getNonVariantName());
                } else {
                    Set<String> set = new HashSet<>();
                    set.add(line.getNonVariantName());
                    stationCorrespondence.put(stationDTO, set);
                }
            });
        }
        return stationCorrespondence.entrySet()
                .stream()
                .map((entry) -> new StationCorrespondence(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<StationCorrespondence> getBestStations() {
        return this.getAllStationsCorrespondence().stream()
                .sorted(Comparator.comparingInt(StationCorrespondence::getNbStations).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private StationDTO stationToStationDTO(Station station) {
        return new StationDTO(station.getName(), station.getCoordinates().getLongitude(),
                station.getCoordinates().getLatitude());
    }
}
