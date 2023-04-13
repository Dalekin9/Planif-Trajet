package com.planifcarbon.backend.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.springframework.stereotype.Component;
import com.planifcarbon.backend.parser.Parser;
import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.StationDTO;
import jakarta.annotation.PostConstruct;

@Component
public final class MetroMap {
    private final Map<Node, Set<Segment>> graph;
    private final Map<String, MetroLine> lines;
    private final Map<String, Station> stations;

    /**
     * {@summary Main constructor.}
     */
    public MetroMap() {
        graph = new HashMap<Node, Set<Segment>>();
        lines = new HashMap<String, MetroLine>();
        stations = new HashMap<String, Station>();
    }

    public Map<String, Station> getStations() { return stations; }
    public Set<Station> getAllStations() { return getStations().values().stream().collect(HashSet::new, HashSet::add, HashSet::addAll); }

    public Map<String, MetroLine> getLines() { return lines; }

    public Map<Node, Set<Segment>> getGraph() { return graph; }

    public Station getStationByName(String stationName) { return this.stations.getOrDefault(stationName, null); }

    /**
     * {@summary Return the list of nodes.}
     */
    public Set<Node> getNodes() { return graph.keySet(); }

    /**
     * {@summary Return the list of segments.}
     *
     * @return the list of segments
     */
    public Set<Segment> getSegments(Node node) { return graph.get(node); }
    public Set<Segment> getSegmentsMetro(Node node) {
        return graph.get(node).stream().filter(segment -> segment instanceof SegmentMetro).collect(HashSet::new, HashSet::add,
                HashSet::addAll);
    }


    // Build functions
    // --------------------------------------------------------------------------------------------------------------------
    /**
     * {@summary Build this.}
     * It initializes all the fields.
     * "@PostConstruct" is used to make sure that this method is called after the constructor.
     */
    @PostConstruct
    public void initializeFields() {
        // get values from parser
        String metroFile = "classpath:data/map_data.csv";
        String scheduleFile = "classpath:data/timetables.csv";
        try {
            Parser.parse(metroFile, scheduleFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found when parsing files " + e);
            return;
        } catch (IOException e) {
            System.out.println("IO error when parsing files " + e);
            return;
        }
        Set<StationDTO> stations = Parser.getStations(); // To be used for walk segments.
        Map<String, String> metroLinesTerminus = Parser.getMetroLines();
        stations.forEach(stationDTO -> {
            Station station = this.stationDTOtoStation(stationDTO);
            this.stations.put(station.getName(), station);
        });
        Set<SegmentMetroDTO> segmentMetroDTOS = Parser.getSegmentMetro();
        Map<String, List<Integer>> schedules = Parser.getMetroLineSchedules();
        Map<String, Set<Station>> metroLines = new HashMap<String, Set<Station>>();

        // use values from parser to build this
        addSegmentMetroToLinesAndGraph(segmentMetroDTOS, metroLines);
        setMetroLineSchedules(metroLines, metroLinesTerminus, schedules);
        diffuseTrainTimeFromTerminus(metroLinesTerminus);
        addAllWalkSegments(getAllStations());
    }

    /**
     * Calculates metroLines and graph with metro segments.
     */
    private void addSegmentMetroToLinesAndGraph(Set<SegmentMetroDTO> segmentMetroDTOS, Map<String, Set<Station>> metroLines) {
        segmentMetroDTOS.forEach(segment -> {
            Station start = this.stations.get(segment.getStart().getName());
            Station end = this.stations.get(segment.getEnd().getName());
            if (metroLines.containsKey(segment.getLine())) {
                metroLines.get(segment.getLine()).add(start);
                metroLines.get(segment.getLine()).add(end);
            } else {
                Set<Station> set = new HashSet<Station>();
                set.add(start);
                set.add(end);
                metroLines.put(segment.getLine(), set);
            }
            this.addSegmentMetro(start, end, segment.getDistance(), segment.getDuration(), segment.getLine());
        });
    }

    /**
     * Set metro line schedules.
     */
    private void setMetroLineSchedules(Map<String, Set<Station>> metroLines, Map<String, String> metroLinesTerminus,
            Map<String, List<Integer>> schedules) {
        metroLines.forEach((key, value) -> {
            List<Integer> schedule = schedules.getOrDefault(key, new ArrayList<>());
            this.lines.put(key, new MetroLine(key, value, schedule, this.stations.get(metroLinesTerminus.get(key))));
        });
    }

    /**
     * Calculate time for train to get to the station from terminus in each station.
     */
    private void diffuseTrainTimeFromTerminus(Map<String, String> metroLinesTerminus) {
        metroLinesTerminus.forEach((key, value) -> {
            Node node;
            Segment segment;
            node = new Station(value, 0, 0);
            Station terminusStation = this.stations.get(value);
            MetroLine metroLine = this.lines.get(key);
            if (terminusStation != null && metroLine != null) {
                ScheduleKey scheduleKey = new ScheduleKey(terminusStation, metroLine);
                terminusStation.addSchedule(scheduleKey, 0);
                do {
                    segment = this.getSegments(node).stream()
                            .filter((sgt) -> sgt.getClass().equals(SegmentMetro.class) && ((SegmentMetro) sgt).getLine().equals(key))
                            .findFirst().orElse(null);
                    if (segment != null) {
                        terminusStation = (Station) segment.getEndPoint();
                        int schedule = ((Station) segment.getStartPoint()).getScheduleForKey(scheduleKey) + segment.getDuration();
                        terminusStation.addSchedule(scheduleKey, schedule);
                        node = segment.getEndPoint();
                    }
                } while (segment != null);
            }
        });
    }
    /**
     * Calculate graph with walk segments
     */
    private void addAllWalkSegments(Set<Station> stations) {
        stations.forEach(station -> {
            Station start = this.stations.get(station.getName());
            stations.forEach(station2 -> {
                Station end = this.stations.get(station2.getName());
                if (start != end) {
                    this.addSegmentWalk(start, end, start.distanceTo(end));
                }
            });
        });
    }

    /**
     * {@summary Add a new node to the graph.}
     */
    public void addNode(String name, double latitude, double longitude, Class<? extends Node> nodeClass) {
        if (nodeClass == null) {
            throw new IllegalArgumentException("nodeClass must not be null");
        }
        try {
            Node node = nodeClass.getDeclaredConstructor(String.class, double.class, double.class).newInstance(name, latitude, longitude);
            graph.put(node, new HashSet<Segment>());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            // System.out.println("Error while creating a new node " + e);
            throw new IllegalArgumentException("Error while creating a new node " + e);
        }
    }

    /**
     * {@summary Add a new segment to the graph.}
     * It need both nodes to be in the graph.
     *
     * @param segment segment to add
     */
    private void addSegment(Segment segment) {
        if (graph.containsKey(segment.getStartPoint())) {
            graph.get(segment.getStartPoint()).add(segment);
        } else {
            Set<Segment> set = new HashSet<Segment>();
            set.add(segment);
            graph.put(segment.getStartPoint(), set);
        }
    }

    /**
     * {@summary Add a new segment to the graph.}
     * It need both nodes to be in the graph.
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @param distance  distance between the 2 nodes
     */
    public void addSegmentWalk(Node startNode, Node endNode, double distance) {
        addSegment(new SegmentWalk(startNode, endNode, distance));
    }

    /**
     * {@summary Add a new segment to the graph.}
     * It need both nodes to be in the graph.
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @param distance  distance between the 2 nodes
     * @param duration  duration between the 2 nodes
     * @param line      name of the line of the metro
     */
    public void addSegmentMetro(Node startNode, Node endNode, double distance, int duration, String line) {
        addSegment(new SegmentMetro(startNode, endNode, distance, duration, line));
    }

    // Main functions
    // ---------------------------------------------------------------------------------------------------------------------
    public List<Node> routeCalculation(String startNodeName, String endNodeName) {
        // TODO get the 2 nodes from their names
        // TODO call Dijkstra
        // TODO return the list of nodes to go through
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // Adapters functions
    // -----------------------------------------------------------------------------------------------------------------
    Station stationDTOtoStation(StationDTO dto) { return new Station(dto.getName(), dto.getLatitude(), dto.getLongitude()); }
}
