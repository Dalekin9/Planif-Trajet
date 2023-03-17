package main.java.com.planifcarbon.backend.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Parser{

    public static String[] splitString(String reg, String line) {
        return line.split(reg);
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
        //TODO
        return null;
    }

    public static Collection<Collection<Object>> getMetroStations(String filepath) throws FileNotFoundException {
        //TODO
        return null;
    }

    public static Collection<Collection<Object>> getSchedule(String filepath) throws FileNotFoundException {
        //TODO
        return null;
    }
}