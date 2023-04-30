package com.planifcarbon.backend.dtos;

import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Used to store dijkstra search result.
 */
@ExcludeFromJacocoGeneratedReport
public class DjikstraSearchResultDTO {
    private final NodeDTO start;
    private final NodeDTO end;
    private final int duration;
    private final String metroLine;
    private final String terminusStation; // To show the direction to the user



    /**
     * Constructs a new DjikstraSearchResultDTO object.
     *
     * @param start The NodeDTO object representing the start node of the search result.
     * @param end The NodeDTO object representing the end node of the search result.
     * @param duration The total duration of the path from start to end.
     * @param metroLine The metro line used in the search result.
     * @param terminusStation The terminus station of the metro line used in the search result.
     */
    public DjikstraSearchResultDTO(NodeDTO start, NodeDTO end, int duration, String metroLine, String terminusStation) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.metroLine = metroLine;
        this.terminusStation = terminusStation;
    }

    /**
     * Returns the duration of the path from start to end.
     *
     * @return The duration of the path.
     */
    public int getDuration() {
        return duration;
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
