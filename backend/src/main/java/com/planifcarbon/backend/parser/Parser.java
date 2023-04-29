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
import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.NodeDTO;

/**
 * {@summary Its static methods are used to parse CSV files}
 */
public class Parser {

    private static final Set<NodeDTO> stations = new HashSet<NodeDTO>();
    private static final Set<SegmentMetroDTO> segmentMetro = new HashSet<SegmentMetroDTO>();
    private static final Map<String, String> metroLines = new HashMap<String, String>();
    private static final Map<String, List<Integer>> metroLineSchedules = new HashMap<String, List<Integer>>();

    /**
     * {@summary Parse all CSV file.}
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void parse(String metroFile, String scheduleFile) throws FileNotFoundException, IOException {
        Parser.calculateStationsAndSegments(metroFile);
        Parser.calculateSchedules(scheduleFile);
    }

    // private ----------------------------------------------------------------

    /** Tool function to split a String */
    static String[] splitString(String reg, String line) { return line.split(reg); }

    /** Tool function used to parse time from hh:mm:ss.ms to ms */
    // TODO rename to make it more different from timeStringToInt
    static int durationStringToInt(String str) {
        double seconds = Double.parseDouble(str.replace(':', '.')) * 10;
        return (int) Math.ceil(seconds);
    }
    /** Tool function used to parse time from hh:mm to ms */
    // TODO rename to make it more different from durationStringToInt
    static int timeStringToInt(String str) {
        String[] time = splitString(":", str);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours = hours * 60 * 60; // hours to seconds
        minutes = minutes * 60;

        return hours + minutes;
    }

    /**
     * {@summary Parse station &#38; segment data from a CSV file.}
     * 
     * @param filePath the path of the CSV file
     * @throws FileNotFoundException
     * @throws IOException
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
     * {@summary Parse station &#38; segment data from a CSV file.}
     * 
     * @param scheduleFile the path of the CSV file
     * @throws FileNotFoundException
     * @throws IOException
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

    public static Map<String, String> getMetroLines() { return metroLines; }

    public static Map<String, List<Integer>> getMetroLineSchedules() { return metroLineSchedules; }

    public static Set<SegmentMetroDTO> getSegmentMetro() { return segmentMetro; }

    public static Set<NodeDTO> getStations() { return stations; }
}
