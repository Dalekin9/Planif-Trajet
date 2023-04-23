package com.planifcarbon.backend.model;

/**
 * {@summary A class that plays a role in representing the results of the dijkstra's algorithm
 * when searching for the optimal route in terms of duration:
 * Map(Node nodeTo, SearchResultBestDuration result).}
 */
public class SearchResultBestDuration {
    private final Node nodeDestination;
    private final int arrivalTime;
    private final MetroLine line;

    /**
     * {@summay Main constructor.}
     * 
     * @param nodeFrom    node from which movement was made
     * @param arrivalTime arrival time from nodeFrom to key station
     * @param line        metro line that was used
     */
    public SearchResultBestDuration(Node nodeFrom, int arrivalTime, MetroLine line) {
        this.nodeDestination = nodeFrom;
        this.arrivalTime = arrivalTime;
        this.line = line;
    }

    public Node getNodeDestination() { return nodeDestination; }
    public int getArrivalTime() { return arrivalTime; }
    public MetroLine getMetroLine() { return line; }
}
