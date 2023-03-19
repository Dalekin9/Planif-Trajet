package main.java.com.planifcarbon.backend.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Parser{

    public static String[] splitString(String reg, String line) {
        return line.split(reg);
    }

    public static int durationStringToInt(String str){
        String[] duration = splitString(":", str);
        int minutes = Integer.parseInt(duration[0]);
        int seconds = Integer.parseInt(duration[1]);

        seconds = seconds * 1000;           // Seconds to milliseconds
        minutes = minutes * 60 * 1000;      // Minutes to milliseconds

        return minutes + seconds;
    }

    public static int timeStringToInt(String str){
        String[] time = splitString(":", str);
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);

        hours = hours * 60 * 60 * 1000;
        minutes = minutes *60 * 1000;

        return hours + minutes;
    }

    public static int distanceStringToInt(String str){
        float distance = Float.parseFloat(str);
        distance = distance * 1000;             // Kilometers to meters

        return Math.round(distance);
    }

    public static Collection<Collection<Object>> getNodeList(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins);
        String[] line;

        Collection<Collection<Object>> nodes = new ArrayList<>();
        ArrayList<Object> currentNode;
        String[] coords;

        while(scan.hasNextLine()) {
            //TODO: Check for duplicates

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
        Scanner scan = new Scanner(ins);
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
        //TODO
        return null;
    }

    public static Collection<Collection<Object>> getSchedule(String filepath) throws FileNotFoundException {
        InputStream ins = new FileInputStream(filepath);
        Scanner scan = new Scanner(ins);
        String[] line;

        Collection<Collection<Object>> timeTable = new ArrayList<>();
        Collection<Object> currentTime;

        while(scan.hasNextLine()) {

            line = splitString(";", scan.nextLine());
            //Each line contains 3 elements : line, terminusStation, time

            currentTime = new ArrayList<>();

            currentTime.add(line[0]);
            currentTime.add(line[1]);
            currentTime.add(timeStringToInt(line[2]));

            timeTable.add(currentTime);
        }

        return timeTable;
    }
}