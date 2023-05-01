package com.planifcarbon.backend.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.dtos.SegmentMetroDTO;

/**
 * {Its static methods are used to parse CSV files}
 * It is respecting singleton pattern.
 */
public class Parser {

    private static final Set<NodeDTO> stations = new HashSet<>();
    private static final Set<SegmentMetroDTO> segmentMetro = new HashSet<>();
    private static final Map<String, String> metroLines = new HashMap<>();
    private static final Map<String, List<Integer>> metroLineSchedules = new HashMap<>();

    public static final Parser instance = new Parser();

    private Parser() {}

    /**
     * {Parse all CSV file.}
     *
     * @param metroFile file representing the metro network.
     * @param scheduleFile file representing the metro schedules.
     * @throws FileNotFoundException thrown when provided file not found.
     * @throws IOException thrown when an error occurred when opening file.
     */
    public void parse(String metroFile, String scheduleFile) throws FileNotFoundException, IOException {
        Parser.calculateStationsAndSegments(metroFile);
        Parser.calculateSchedules(scheduleFile);
    }

    // private ----------------------------------------------------------------

    /**
     * Tool function to split a String
     *
     * @param reg the regular expression to split the String
     * @param line the line to split
     * @return the String array after split
     */
    static String[] splitString(String reg, String line) { return line.split(reg); }

    /**
     * Tool function used to parse time from hh:mm:ss.ms to ms
     *
     * @param str the String representation of time in format hh:mm:ss.ms
     * @return the time in milliseconds
     */
    static int durationStringToInt(String str) {
        double seconds = Double.parseDouble(str.replace(':', '.')) * 10;
        return (int) Math.ceil(seconds);
    }

    /**
     * Tool function used to parse time from hh:mm to ms
     *
     * @param str the String representation of time in format hh:mm
     * @return the time in milliseconds
     */
    static int timeStringToInt(String str) {
        String[] time = splitString(":", str);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours = hours * 60 * 60; // hours to seconds
        minutes = minutes * 60;

        return hours + minutes;
    }

    /**
     * {Parse station &#38; segment data from a CSV file.}
     * 
     * @param filePath the path of the CSV file
     * @throws FileNotFoundException thrown when file is not found.
     * @throws IOException thrown when an error occurred when opening file.
     */
    static void calculateStationsAndSegments(String filePath) throws FileNotFoundException, IOException {
        // try with safe close.
        try (InputStream ins = Parser.class.getClassLoader().getResourceAsStream(filePath);
                Scanner scan = new Scanner(ins, StandardCharsets.UTF_8)) {
            String[] currentLine;
            String[] coords;
            NodeDTO start;
            NodeDTO end;
            while (scan.hasNextLine()) {
                currentLine = splitString(";", scan.nextLine());
                // Each line contains 7 elements : name1, coords1, name2, coords2, line, time, dist
                coords = splitString(",", currentLine[1]);
                start = new NodeDTO(currentLine[0], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
                coords = splitString(",", currentLine[3]);
                end = new NodeDTO(currentLine[2], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
                stations.add(start);
                stations.add(end);
                segmentMetro.add(new SegmentMetroDTO(start, end, durationStringToInt(currentLine[5]), Double.parseDouble(currentLine[6]) / 10,
                        currentLine[4]));
                metroLines.putIfAbsent(currentLine[4], currentLine[0]);
            }
        }
    }

    /**
     * {Parse station &#38; segment data from a CSV file.}
     * 
     * @param scheduleFile the path of the CSV file
     * @throws FileNotFoundException thrown when provided file not found.
     * @throws IOException thrown when an error occurred when opening file.
     */
    static void calculateSchedules(String scheduleFile) throws FileNotFoundException, IOException {
        // try with safe close.
        try (InputStream ins = Parser.class.getClassLoader().getResourceAsStream(scheduleFile);
                Scanner scan = new Scanner(ins, StandardCharsets.UTF_8)) {
            String[] currentLine;
            String variantKey;
            while (scan.hasNextLine()) {
                currentLine = splitString(";", scan.nextLine());
                // Each line contains 3 elements : line, terminusStation, time
                variantKey = currentLine[0] + " variant " + currentLine[3];
                if (metroLineSchedules.containsKey(variantKey)) {
                    metroLineSchedules.get(variantKey).add(timeStringToInt(currentLine[2]));
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(timeStringToInt(currentLine[2]));
                    metroLineSchedules.put(variantKey, list);
                }
            }
        }
    }

    /**
     * Returns a map of all metro lines, with keys representing the name of the line
     * and values representing the color of the line.
     *
     * @return a map of all metro lines
     */
    public static Map<String, String> getMetroLines() { return metroLines; }

    /**
     * Returns a map of all metro line schedules, with keys representing the name of the line
     * and values representing a list of integer values, each integer value representing the
     * duration in seconds between each stop along the line.
     *
     * @return a map of all metro line schedules
     */
    public static Map<String, List<Integer>> getMetroLineSchedules() { return metroLineSchedules; }

    /**
     * Returns a set of all metro segments, with each segment represented as a SegmentMetroDTO object.
     *
     * @return a set of all metro segments
     */
    public static Set<SegmentMetroDTO> getSegmentMetro() { return segmentMetro; }

    /**
     * Returns a set of all metro stations, with each station represented as a NodeDTO object.
     *
     * @return a set of all metro stations
     */
    public static Set<NodeDTO> getStations() { return stations; }
}
