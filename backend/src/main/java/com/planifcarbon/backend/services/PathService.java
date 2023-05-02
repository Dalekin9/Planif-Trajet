package com.planifcarbon.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.planifcarbon.backend.dtos.DjikstraSearchResultDTO;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.model.DataSegment;
import com.planifcarbon.backend.model.MetroMap;
import com.planifcarbon.backend.model.Node;
import com.planifcarbon.backend.model.PersonalizedNode;
import com.planifcarbon.backend.model.Station;

/**
 * {@summary Service used by the controller to communicate with the view.}
 * It transforms the data from the model so that it fit the one used by the view.
 * It uses the djikstra algorithm to give the best path.
 */
@Service
public class PathService {
    /**
     * Main data object
     */
    private final MetroMap metroMap;

    /**
     * Creates a new instance of PathService with the provided MetroMap.
     *
     * @param metroMap the MetroMap used to calculate the paths.
     */
    public PathService(MetroMap metroMap) {
        this.metroMap = metroMap;
    }

    /**
     * calculate the best path using dijkstra.
     *
     * @param start starting position
     * @param end ending position
     * @param time departure time
     * @param method best path using time / distance
     * @param transportation choose between (metro, metro and walk, walk)
     * @return list of nodes which represents best path using dijkstra.
     */
    public List<DjikstraSearchResultDTO> getBestPath(String start, String end, int time, String method,
                                                     String transportation) {
        Node startNode = this.getNode(start);
        Node endNode = this.getNode(end);
        boolean metro = false;
        boolean walk = false;
        switch (transportation) {
            case "METRO" -> metro = true;
            case "METRO_FOOT" -> {
                metro = true;
                walk = true;
            }
            case "FOOT" -> walk = true;
            default -> {
                metro = true;
                walk = true;
            }
        }
        boolean bestTimePath = method.equalsIgnoreCase("TIME");
        List<DataSegment> result = this.metroMap.getSegmentsFromPath(startNode, endNode, bestTimePath ? time: 0, metro, walk, bestTimePath);
        List<DataSegment> groupedDataSegments = this.groupWalkingDataSegments(result);
        return this.dataSegmentsToDijkstraPath(groupedDataSegments);
    }

    /**
     * Group walking segemnts.
     *
     * @param dataSegments path returned from dijkstra
     * @return list of data segments where walking segments are grouped.
     */
    private List<DataSegment> groupWalkingDataSegments(List<DataSegment> dataSegments) {
        List<DataSegment> result = new ArrayList<>();
        DataSegment previous = null;
        for (DataSegment dataSegment : dataSegments) {
            if (dataSegment.getLine() == null) {
                if (previous == null) {
                    previous = dataSegment;
                } else {
                    previous = new DataSegment(previous.getNodeStart(), dataSegment.getNodeEnd(),
                            previous.getDepartureTime(), dataSegment.getArrivalTime(), null,
                            previous.getDistance() + dataSegment.getDistance());
                }
            } else {
                if (previous != null) {
                    result.add(previous);
                }
                previous = null;
                result.add(dataSegment);
            }
        }
        if (previous != null) {
            result.add(previous);
        }
        return result;
    }

    /**
     * Transform data segments to dijkstra search result which will be given to front later.
     *
     * @param dataSegments the best path result including metro and walking data segments.
     * @return list dijkstra search result dto to print in the front end part.
     */
    private List<DjikstraSearchResultDTO> dataSegmentsToDijkstraPath(List<DataSegment> dataSegments) {
        return dataSegments.stream().map((segment) -> {
            String lineName = null;
            String terminusStation = null;
            if (segment.getLine() != null) {
                lineName = segment.getLine().getNonVariantName();
                List<Station> stations = new ArrayList<>(segment.getLine().getStations());
                terminusStation = stations.get(stations.size() - 1).getName();
            }
            return new DjikstraSearchResultDTO(nodeToNodeDto(segment.getNodeStart()),
                    nodeToNodeDto(segment.getNodeEnd()), segment.getArrivalTime(), lineName, terminusStation);
        }).collect(Collectors.toList());
    }

    /**
     * Get the node (custom, station) from network.
     *
     * @param start starting position to go from which can be station or personalized node.
     * @return station if start is station name or personalized node if start is coordinates.
     */
    private Node getNode(String start) {
        Station station = this.metroMap.getStationByName(start);
        if (station != null) {
            return station;
        }
        String[] parts = start.substring(1, start.length() - 1).split(", ");
        return new PersonalizedNode(start, Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
    }

    /**
     * Transform node to node dto.
     *
     * @param node node to transform to data transfer object.
     * @return node dto from the station.
     */
    private NodeDTO nodeToNodeDto(Node node) {
        return new NodeDTO(node.getName(), node.getCoordinates().getLongitude(),
                node.getCoordinates().getLatitude());
    }
}
