package com.planifcarbon.backend.model;

/**
 * {A class that plays a role in representing the results of the dijkstra's algorithm
 * when searching for the optimal route in terms of duration:
 * Map(Node nodeTo, SearchResultBestDuration result).}
 */
public class SearchResultBestWeight {
    private final Node nodeDestination;
    private final double weight;
    private final MetroLine line;

    /**
     * {Main constructor.}
     * 
     * @param nodeFrom     node from which movement was made
     * @param weight       arrival time / distance from nodeFrom to key station
     * @param line         metro line that was used
     */
    public SearchResultBestWeight(Node nodeFrom, double weight, MetroLine line) {
        this.nodeDestination = nodeFrom;
        this.weight = weight;
        this.line = line;
    }

    /**
     * Returns the destination node for the search.
     *
     * @return The destination node for the search.
     */
    public Node getNodeDestination() { return nodeDestination; }

    /**
     * Returns the arrival time / distance at the destination node.
     *
     * @return The arrival time / distance at the destination node.
     */
    public double getWeight() { return weight; }

    /**
     * Returns the metro line used to reach the destination node.
     *
     * @return The metro line used to reach the destination node.
     */
    public MetroLine getMetroLine() { return line; }

    /**
     * Returns a string representation of the `SearchResultBestDuration` object.
     *
     * @return A string representation of the `SearchResultBestDuration` object.
     */
    public String toString() {
        return "SearchResultBestDuration{" + "nodeDestination=" + nodeDestination + ", weight=" + weight + ", line=" + line + '}';
    }
}
