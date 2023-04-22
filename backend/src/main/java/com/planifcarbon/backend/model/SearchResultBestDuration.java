package com.planifcarbon.backend.model;

/**
 * {@summary A class that plays a role in representing the results of the Dijkstra's algorithm
 * when searching for the optimal route in terms of duration:
 * Map(Node nodeTo, SearchResultBestDuration result).}
 */
public class SearchResultBestDuration {
    private Node nodeFrom;
    private int arrivalTime;
    private MetroLine line;

    /**
     * {@summay Main constructor.}
     * 
     * @param  nodeFrom node from which movement was made
     * @param  arrivalTime arrival time from nodeFrom to key station
     * @param  line metro line that was used
     */
    public SearchResultBestDuration(Node nodeFrom, int arrivalTime, MetroLine line) {
        this.nodeFrom = nodeFrom;
        this.arrivalTime = arrivalTime;
        this.line = line;
    }

    public Node getNodeFrom() { return nodeFrom; }
    public int getArrivalTime() { return arrivalTime; }
    public MetroLine getMetroLine() { return line; }

    /**
     * {@summay Replace values of class attributes.}
     *
     * @param  node node from which movement was made
     * @param  arrivalTime arrival time from nodeFrom to key station
     * @param  line metro line that was used
     */
    public void replace(Node node, int time, MetroLine l) {
        this.nodeFrom = node;
        this.arrivalTime = time;
        this.line = l;
    }
}
