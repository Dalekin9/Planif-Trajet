package com.planifcarbon.backend.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * {@summary Its static methods are used to parse CSV files}
 */
public class Parser{

    public static String[] splitString(String reg, String line) {
        return line.split(reg);
    }

    public static int durationStringToInt(String str){
        String[] duration = splitString(":", str);
        int minutes = Integer.parseInt(duration[0]);
        int seconds = Integer.parseInt(duration[1]);

        minutes = minutes * 60 ;      // Minutes to seconds

        return minutes + seconds;
    }

    public static int timeStringToInt(String str){
        String[] time = splitString(":", str);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours = hours * 60 * 60 ;       //hours to seconds
        minutes = minutes * 60 ;

        return hours + minutes;
    }

    public static int distanceStringToInt(String str){
        float distance = Float.parseFloat(str);
        distance = distance * 1000;             // Kilometers to meters

        return Math.round(distance);
    }

    public static Collection<Collection<Object>> getNodeList(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] line;

        Collection<Collection<Object>> nodes = new ArrayList<>();
        ArrayList<Object> currentNode;
        String[] coords;

        while(scan.hasNextLine()) {
            line = splitString(";", scan.nextLine());
            //Each line contains 7 elements : name1, coords1, name2, coords2, line, time, dist

            currentNode = new ArrayList<>();
            currentNode.add(line[0]);                       //Adds the name of the first station

            coords = splitString(",", line[1]);         //Splits the coordinates to latitude and longitude
            currentNode.add( Float.parseFloat(coords[0]) );
            currentNode.add( Float.parseFloat(coords[1]) );
            nodes.add(currentNode);                         //Adds the node to the collection

            currentNode = new ArrayList<>();
            currentNode.add(line[2]);

            coords = splitString(",", line[3]);
            currentNode.add( Float.parseFloat(coords[0]) );
            currentNode.add( Float.parseFloat(coords[1]) );
            nodes.add(currentNode);
        }
        return nodes;
    }

    public static Collection<Collection<Object>> getSegmentList(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] line;

        Collection<Collection<Object>> segments = new ArrayList<>();
        ArrayList<Object> currentSegment;

        while(scan.hasNextLine()) {

            line = splitString(";", scan.nextLine());
            //Each line contains 7 elements : name1, coords1, name2, coords2, line, time, dist

            currentSegment = new ArrayList<>();
            currentSegment.add(line[0]);                        //Name of the first station
            currentSegment.add(line[2]);                        //Name of the second station
            currentSegment.add(line[4]);                        //Name of the train line

            currentSegment.add(durationStringToInt(line[5]));   //Travel time between stations
            currentSegment.add(distanceStringToInt(line[6]));   //Distance between stations

            segments.add(currentSegment);                        //Adds the segment to the collection
        }
        return segments;
    }

    public static Collection<Collection<Object>> getMetroStations(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] line;

        Collection<Collection<Object>> segments = new ArrayList<>();
        ArrayList<Object> currentSegment;
        String[] coords;

        while(scan.hasNextLine()) {

            line = splitString(";", scan.nextLine());
            //Each line contains 7 elements : name1, coords1, name2, coords2, line, time, dist

            currentSegment = new ArrayList<>();

            currentSegment.add(line[0]);                        //Name of the first station
            coords = splitString(",", line[1]);             //Splits the coordinates to latitude and longitude
            currentSegment.add( Float.parseFloat(coords[0]) );
            currentSegment.add( Float.parseFloat(coords[1]) );

            currentSegment.add(line[2]);                        //Name of the second station
            coords = splitString(",", line[3]);
            currentSegment.add( Float.parseFloat(coords[0]) );
            currentSegment.add( Float.parseFloat(coords[1]) );

            currentSegment.add(line[4]);                        //Name of the train line

            currentSegment.add(durationStringToInt(line[5]));   //Travel time between stations
            currentSegment.add(distanceStringToInt(line[6]));   //Distance between stations

            segments.add(currentSegment);

        }
        return segments;
    }

    public static Map<String,Map<String,Collection<Integer>>> getSchedule(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins, StandardCharsets.UTF_8);
        String[] line;

        Map<String,Map<String, Collection<Integer>>> timeTable = new HashMap<>();
        Map<String, Collection<Integer>> lineMap;
        Collection<Integer> timesOfStation;

        String currentLine;
        String currentStation;
        int currentTime;

        while(scan.hasNextLine()) {

            line = splitString(";", scan.nextLine());
            //Each line contains 3 elements : line, terminusStation, time

            currentLine = line[0];
            currentStation = line[1];
            currentTime = timeStringToInt(line[2]);

            lineMap = timeTable.get(currentLine);

            if(lineMap != null) {
                timesOfStation = lineMap.get(currentStation);
            }else{
                timesOfStation = null;
            }

            if (lineMap == null){
                timeTable.put(currentLine, new HashMap<>());
                timeTable.get(currentLine).put(currentStation, new ArrayList<>(currentTime));
            }else if(timesOfStation == null){
                lineMap.put(currentStation, new ArrayList<>(currentTime));
            }else{
                timesOfStation.add(currentTime);
            }
        }
        return timeTable;
    }
}