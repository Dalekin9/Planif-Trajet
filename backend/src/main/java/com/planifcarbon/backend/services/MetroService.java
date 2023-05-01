package com.planifcarbon.backend.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.planifcarbon.backend.dtos.MetroDTO;
import com.planifcarbon.backend.dtos.MetroLineStationSchedulesDTO;
import com.planifcarbon.backend.dtos.MetroScheduleDTO;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.dtos.StationCorrespondence;
import com.planifcarbon.backend.model.MetroLine;
import com.planifcarbon.backend.model.MetroMap;
import com.planifcarbon.backend.model.Station;

/**
 * {Service used by the controller to communicate with the view.}
 * It transforms the data from the model so that it fit the one used by the view.
 */
@Service
public class MetroService {
    /**
     * Main data object
     */
    private final MetroMap metroMap;

    /**
     * Constructs a new `MetroService` instance with the given `MetroMap`.
     *
     * @param metroMap the `MetroMap` instance to use for the `MetroService`
     */
    public MetroService(MetroMap metroMap) {
        this.metroMap = metroMap;
    }

    /**
     * Get the list of available metro lines.
     *
     * @return all metro lines from network.
     */
    public List<MetroDTO> getMetros() {
        return this.metroMap.getLines()
                .values()
                .stream()
                .map(MetroLine::getNonVariantName)
                .distinct()
                .map(MetroDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Get the metro using its name.
     *
     * @param metroName metro name to get metro and its information (stations and schedules).
     * @return metro line with its information.
     */
    public MetroDTO getMetroByName(String metroName) {
        List<MetroLine> metroLinesVariant = this.metroMap.getLines()
                .values()
                .stream()
                .filter(metroLine -> metroLine.getNonVariantName().equals(metroName))
                .collect(Collectors.toList());
        List<NodeDTO> stations = metroLinesVariant.stream().flatMap(metroLine -> metroLine.getStations().stream())
                .map(this::stationToStationDTO)
                .distinct()
                .collect(Collectors.toList());
        List<MetroScheduleDTO> schedules = metroLinesVariant.stream().map(metroLine -> new MetroScheduleDTO(metroName
                , metroLine.getTerminusStation().getName(), metroLine.getSchedules())).collect(Collectors.toList());
        stations.sort(Comparator.comparing(NodeDTO::getName));
        return new MetroDTO(metroName, stations, schedules);
    }

    /**
     * Get all stations correspondences.
     *
     * @return for each station its metro lines correspondences.
     */
    public List<StationCorrespondence> getAllStationsCorrespondence() {
        Map<NodeDTO, Set<String>> stationCorrespondence = new HashMap<>();
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

    /**
     * Get the best stations in the network.
     *
     * @return list of best served stations in the network.
     */
    public List<StationCorrespondence> getBestStations() {
        return this.getAllStationsCorrespondence().stream()
                .sorted(Comparator.comparingInt(StationCorrespondence::getNbStations).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Get all stations from the network.
     *
     * @return list of all stations in the network.
     */
    public List<NodeDTO> getAllStations() {
        return this.metroMap.getAllStations().stream().map(this::stationToStationDTO).collect(Collectors.toList());
    }

    /**
     * Transform station to station dto.
     *
     * @param station station to transform to data transfer object.
     * @return node dto from the station.
     */
    private NodeDTO stationToStationDTO(Station station) {
        return new NodeDTO(station.getName(), station.getCoordinates().getLongitude(),
                station.getCoordinates().getLatitude());
    }


    /**
     * Returns the schedule for a specific station on a specific metro line.
     *
     * @param stationName the name of the station
     * @param metroLine   the name of the metro line
     * @return a MetroLineStationSchedulesDTO object representing the schedule for the specified station on the
     * specified metro line
     */
    public MetroLineStationSchedulesDTO getLineSchedulesForStation(String stationName, String metroLine) {
        List<Integer> list = new ArrayList<>();
        Station station = this.metroMap.getStationByName(stationName);
        station.getSchedules()
                .entrySet()
                .stream()
                .filter((entry) -> entry.getKey().getMetroLine().getNonVariantName().equalsIgnoreCase(metroLine))
                .forEach((entry) -> {
                    List<Integer> schedules =
                            entry.getKey().getMetroLine().getSchedules().stream().map((schedule) -> schedule + entry.getValue()).collect(Collectors.toList());
                    list.addAll(schedules);
                });
        List<Integer> listOfSchedules = list.stream().sorted().distinct().collect(Collectors.toList());
        return new MetroLineStationSchedulesDTO(metroLine, stationName, listOfSchedules);
    }
}
