package com.planifcarbon.backend.model;

import org.springframework.lang.Nullable;

public class DataSegment {
    private final Node nodeStart;
    private final Node nodeEnd;
    private final int arrivalTime;
    private final int departureTime;
    private final @Nullable MetroLine line;
    private final double distance;

    public DataSegment(Node nodeStart, Node nodeEnd, int arrivalTime, int departureTime, @Nullable MetroLine line, double distance) {
        this.nodeStart = nodeStart;
        this.nodeEnd = nodeEnd;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.line = line;
        this.distance = distance;
    }

    public Node getNodeStart() { return nodeStart; }
    public Node getNodeEnd() { return nodeEnd; }
    public int getArrivalTime() { return arrivalTime; }
    public int getDepartureTime() { return departureTime; }
    public @Nullable MetroLine getLine() { return line; }
    public double getDistance() { return distance; }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DataSegment))
            return false;
        DataSegment ds = (DataSegment) o;
        return ds.nodeStart.equals(this.nodeStart) && ds.nodeEnd.equals(this.nodeEnd) && ds.arrivalTime == this.arrivalTime
                && ds.departureTime == this.departureTime && ((ds.line == null && this.line == null) || ds.line.equals(this.line))
                && ds.distance == this.distance;
    }

    @Override
    public String toString() {
        return "DataSegment{" + "nodeStart=" + nodeStart + ", nodeEnd=" + nodeEnd + ", arrivalTime=" + arrivalTime + ", departureTime="
                + departureTime + ", line=" + line + ", distance=" + distance + '}';
    }
}