package com.planifcarbon.backend.parser;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * {@summary Its static methods are used to parse CSV files}
 */
@Component
public class Parser {

    private static final Set<StationDTO> stations = new HashSet<>();
    private static final Set<SegmentMetroDTO> segmentMetro = new HashSet<>();
    private static final Map<String, String> metroLines = new HashMap<>();
    private static final Map<VariantKey, List<Integer>> metroLineSchedules = new HashMap<>();

    static String[] splitString(String reg, String line) {
        return line.split(reg);
    }

    static int durationStringToInt(String str) {
        String[] duration = splitString(":", str);
        int hours, minutes, seconds;
        if (duration.length == 3) {
            hours = Integer.parseInt(duration[0]);
            minutes = Integer.parseInt(duration[1]);
            seconds = Integer.parseInt(duration[2]);
        } else {
            hours = 0;
            minutes = Integer.parseInt(duration[0]);
            seconds = Integer.parseInt(duration[1]);
        }
        return hours * 60 * 60 + minutes * 60 + seconds;
    }

    static int timeStringToInt(String str) {
        String[] time = splitString(":", str);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours = hours * 60 * 60;       //hours to seconds
        minutes = minutes * 60;

        return hours + minutes;
    }

    public static void parse(String metroFile, String scheduleFile) throws FileNotFoundException {
        Parser.calculateStationsAndSegments(metroFile);
        Parser.calculateSchedules(scheduleFile);
    }

    static void calculateStationsAndSegments(String filePath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filePath);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] currentLine;
        String[] coords;
        StationDTO start;
        StationDTO end;
        while (scan.hasNextLine()) {
            currentLine = splitString(";", scan.nextLine());
            //Each line contains 7 elements : name1, coords1, name2, coords2, line, time, dist
            coords = splitString(",", currentLine[1]);
            start = new StationDTO(currentLine[0], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
            coords = splitString(",", currentLine[3]);
            end = new StationDTO(currentLine[2], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
            stations.add(start);
            stations.add(end);
            segmentMetro.add(new SegmentMetroDTO(start,
                    end,
                    durationStringToInt(currentLine[5]),
                    Double.parseDouble(currentLine[6]),
                    currentLine[4]));
            metroLines.putIfAbsent(currentLine[4], currentLine[0]);
        }
    }

    static void calculateSchedules(String scheduleFile) throws FileNotFoundException {
        InputStream ins = new FileInputStream(scheduleFile);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] currentLine;
        VariantKey variantKey;
        while (scan.hasNextLine()) {
            currentLine = splitString(";", scan.nextLine());
            //Each line contains 3 elements : line, terminusStation, time
            variantKey = new Parser.VariantKey(currentLine[1], currentLine[0]);
            if (metroLineSchedules.containsKey(variantKey)) {
                metroLineSchedules.get(variantKey).add(timeStringToInt(currentLine[2]));
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(timeStringToInt(currentLine[2]));
                metroLineSchedules.put(variantKey, list);
            }
        }
    }

    public static Map<String, String> getMetroLines() {
        return metroLines;
    }

    public static Map<VariantKey, List<Integer>> getMetroLineSchedules() {
        return metroLineSchedules;
    }

    public static Set<SegmentMetroDTO> getSegmentMetro() {
        return segmentMetro;
    }

    public static Set<StationDTO> getStations() {
        return stations;
    }

    public static class VariantKey {
        private final String station;
        private final String line;

        public VariantKey(String station, String line) {
            this.station = station;
            this.line = line;
        }

        public String getLine() {
            return line;
        }

        public String getStation() {
            return station;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VariantKey that = (VariantKey) o;
            return Objects.equals(this.station, that.station) && Objects.equals(this.line, that.line);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.station, this.line);
        }
    }
}
