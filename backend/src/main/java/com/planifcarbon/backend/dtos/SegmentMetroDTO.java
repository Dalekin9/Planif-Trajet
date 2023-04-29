package com.planifcarbon.backend.dtos;

import java.util.Objects;
import com.planifcarbon.backend.config.ExcludeFromJacocoGeneratedReport;

/**
 * Temporary Data Transfer Object for the SegmentMetro class.
 */
@ExcludeFromJacocoGeneratedReport
public class SegmentMetroDTO {
    private final NodeDTO start;
    private final NodeDTO end;
    private final int duration;
    private final double distance;
    private final String line;

    public SegmentMetroDTO(NodeDTO start, NodeDTO end, int duration, double distance, String line) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.distance = distance;
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SegmentMetroDTO that = (SegmentMetroDTO) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(line, that.line);
    }

    @Override
    public int hashCode() { return Objects.hash(start, end, line); }

    public NodeDTO getEnd() { return end; }

    public NodeDTO getStart() { return start; }

    public int getDuration() { return duration; }

    public double getDistance() { return distance; }

    public String getLine() { return line; }
}
