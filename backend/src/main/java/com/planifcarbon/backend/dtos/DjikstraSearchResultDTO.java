package com.planifcarbon.backend.dtos;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Used to store dijkstra search result.
 */
@ExcludeFromJacocoGeneratedReport
public class DjikstraSearchResultDTO {
    private final NodeDTO start;
    private final NodeDTO end;
    private final double weight;
    private final String metroLine;
    private final String terminusStation; // To show the direction to the user

    /**
     * Constructs a new DjikstraSearchResultDTO object.
     *
     * @param start The NodeDTO object representing the start node of the search result.
     * @param end The NodeDTO object representing the end node of the search result.
     * @param weight The total weight of the path from start to end.
     * @param metroLine The metro line used in the search result.
     * @param terminusStation The terminus station of the metro line used in the search result.
     */
    public DjikstraSearchResultDTO(NodeDTO start, NodeDTO end, double weight, String metroLine, String terminusStation) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.metroLine = metroLine;
        this.terminusStation = terminusStation;
    }

    /**
     * Returns the weight (duration / time) of the path from start to end.
     *
     * @return The weight of the path.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the NodeDTO object representing the end node of the search result.
     *
     * @return The end node of the search result.
     */
    public NodeDTO getEnd() {
        return end;
    }

    /**
     * Returns the NodeDTO object representing the start node of the search result.
     *
     * @return The start node of the search result.
     */
    public NodeDTO getStart() {
        return start;
    }

    /**
     * Returns the metro line used in the search result.
     *
     * @return The metro line used in the search result.
     */
    public String getMetroLine() {
        return metroLine;
    }

    /**
     * Returns the terminus station of the metro line used in the search result.
     *
     * @return The terminus station of the metro line used in the search result.
     */
    public String getTerminusStation() {
        return terminusStation;
    }
}
