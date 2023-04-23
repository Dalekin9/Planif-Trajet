package com.planifcarbon.backend.services;

import com.planifcarbon.backend.dtos.DjikstraSearchResultDTO;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.model.MetroMap;
import com.planifcarbon.backend.model.Node;
import com.planifcarbon.backend.model.SearchResultBestDuration;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public PathService(MetroMap metroMap) {
        this.metroMap = metroMap;
    }

    public List<DjikstraSearchResultDTO> getBestPath(String start, String end, int time, String method) {
        Map<Node, SearchResultBestDuration> dijkstraPath = this.metroMap.dijkstra(start, end, time);
        Node startNode = this.metroMap.getStationByName(start);
        Node current = this.metroMap.getStationByName(end);
        SearchResultBestDuration currentSearch;
        LinkedList<DjikstraSearchResultDTO> resultingPath = new LinkedList<>();
        while (!current.equals(startNode) && dijkstraPath.get(current) != null) {
            currentSearch = dijkstraPath.get(current);
            resultingPath.addFirst(this.dijkstraSearchResultToDTO(current, currentSearch));
            current = dijkstraPath.get(current).getNodeDestination();
        }
        return resultingPath;
    }

    private DjikstraSearchResultDTO dijkstraSearchResultToDTO(Node current, SearchResultBestDuration search) {
        return new DjikstraSearchResultDTO(this.stationToStationDTO(search.getNodeDestination()),
                this.stationToStationDTO(current), search.getArrivalTime(),
                search.getMetroLine().getNonVariantName(), search.getMetroLine().getTerminusStation().getName());
    }

    private NodeDTO stationToStationDTO(Node node) {
        return new NodeDTO(node.getName(), node.getCoordinates().getLongitude(),
                node.getCoordinates().getLatitude());
    }
}
