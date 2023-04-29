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


    public DjikstraSearchResultDTO(NodeDTO start, NodeDTO end, int duration, String metroLine, String terminusStation) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.metroLine = metroLine;
        this.terminusStation = terminusStation;
    }

    public int getDuration() {
        return duration;
    }

    public NodeDTO getEnd() {
        return end;
    }

    public NodeDTO getStart() {
        return start;
    }

    public String getMetroLine() {
        return metroLine;
    }

    public String getTerminusStation() {
        return terminusStation;
    }
}
