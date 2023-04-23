package com.planifcarbon.backend.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.StationDTO;
import com.planifcarbon.backend.parser.Parser;
import jakarta.annotation.PostConstruct;

/**
 * {@summary Represents the metro map.}
 */
@Component
public final class MetroMap {
    private final Map<Node, Set<Segment>> graph;
    private final Map<String, MetroLine> lines;
    private final Map<String, Station> stations;
    /** Set of all final stations stations (mostly used in tests). */
    private Set<ScheduleKey> scheduleKeys;

    /**
     * {@summary Main constructor.}
     */
    public MetroMap() {
        graph = new HashMap<Node, Set<Segment>>();
        lines = new HashMap<String, MetroLine>();
        stations = new HashMap<String, Station>();
        scheduleKeys = new HashSet<ScheduleKey>();
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
        return graph.get(node).stream().filter(segment -> segment instanceof SegmentMetro).collect(Collectors.toSet());
    }

    /**
     * {@summary Find scheduleKey (final station of matro line) by name.}
     * 
     * @return the scheduleKey (final station of matro line)
     */
    public ScheduleKey getScheduleKeyByName(String name) {
        List<ScheduleKey> ret = this.scheduleKeys.stream().filter(sh -> (sh.getTerminusStation().getName()).equals(name))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ret.get(0);
    }

    /**
     * {@summary Finds the nearest trains departing from the given station after given time.}
     * 
     * @param arrivalTime    time after which need to find nearest trains.
     * @param currentStation station for which need to find nearest trains.
     * @return map contains ending stations (key) and arrival time on given station (value).
     */


    private int getNearestDepartureTime(int arrivalTime, Station currentStation, String lineName) {
        if (null == currentStation) {
            throw new IllegalArgumentException("input should not be null");
        }
        MetroLine line = this.lines.get(lineName);
        int duration = currentStation.getScheduleForKey(new ScheduleKey(line.getTerminusStation(), line));
        return line.getSchedules().stream().filter((departureTime) -> departureTime + duration >= arrivalTime).findFirst()
                .map((departureTime) -> departureTime + duration).orElse(-1);
    }

    /**
     * {@summary Implementation of Dikjstra algorithm.}
     * 
     * @param startNode node from which Dikjstra will be launched
     * @param startTime time of starting the trip
     * @return the map of pairs of nodes (Node Child, Node Parent) which represent the path of most optimized by time
     */
    public Map<Node, SearchResultBestDuration> dijkstra(Node startNode, Node endNode, int startTime, boolean metro, boolean walk) {
        if (null == startNode) {
            throw new IllegalArgumentException("input should not be null");
        }
        if (startTime < 0) {
            throw new IllegalArgumentException("time has to be positive");
        }

        // ============ 0. Create returned structure ===================================================================
        Map<Node, SearchResultBestDuration> path = new HashMap<Node, SearchResultBestDuration>();

        // ============ 1. Set initial weight for all vertex = ꚙ ======================================================
        Set<Node> allNodes = getNodes();

        // =========== 2. Create structure of vertex (let’s call it ‘parens’), which size = nb of vertex ===============
        path.put(startNode, new SearchResultBestDuration(startNode, startTime, null)); // (node, parent)

        // =========== 3. Create and init structure of visited vertex ==================================================
        Map<Node, Boolean> visited = new HashMap<Node, Boolean>();
        allNodes.forEach(node -> {
            visited.put(node, false);
        });

        // ================== 4. Prepare all for startStation ==========================================================
        // Station startStation = getStationByName(startNode.getName());

        // ============= 5. Create priorityQueue where will be stocked pairs (Station, time) ===========================
        PriorityQueue<DjikstraInfo> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(DjikstraInfo::getTime));

        // ----------------- add start station -------------------------------------------------------------------------
        priorityQueue.add(new DjikstraInfo(startNode, startTime));

        // ================= 6. Graph traversal ========================================================================

        while (!priorityQueue.isEmpty()) {
            DjikstraInfo current = priorityQueue.poll();

            int currentTime = current.getTime();
            Station currentStation = (Station) current.getNode();

            visited.replace(currentStation, true);

            if (currentStation.equals(endNode)) {
                return path;
            }

            Set<Segment> neighbors = this.getSegments(currentStation);
            if (!metro) {
                neighbors = neighbors.stream().filter(segment -> !(segment instanceof SegmentMetro)).collect(Collectors.toSet());
            } else if (!walk) {
                neighbors = neighbors.stream().filter(segment -> !(segment instanceof SegmentWalk)).collect(Collectors.toSet());
            }

            for (Segment neighbor : neighbors) {
                // Time to wait for the next train
                int minimalTime = neighbor instanceof SegmentMetro
                        ? this.getNearestDepartureTime(currentTime, (Station) neighbor.getStartPoint(), ((SegmentMetro) neighbor).getLine())
                        : currentTime;
                if (minimalTime == -1) { // is SegmentMetro and no trains
                    continue;
                }
                DjikstraInfo djToTest = new DjikstraInfo(neighbor.getEndPoint(), minimalTime + neighbor.getDuration());
                Optional<DjikstraInfo> djikstraInfo = priorityQueue.stream().filter((dj) -> dj.equals(djToTest)).findFirst();
                if (djikstraInfo.isPresent()) {
                    int compareValue = djikstraInfo.get().compareTo(djToTest);
                    if (compareValue > 0) {
                        djikstraInfo.get().setTime(djToTest.getTime());
                        priorityQueue.remove(djikstraInfo.get());
                        priorityQueue.add(djikstraInfo.get());
                        path.replace(neighbor.getEndPoint(),
                                new SearchResultBestDuration(currentStation, djToTest.getTime(), getLineFromSegment(neighbor)));
                    }
                } else if (!visited.get(neighbor.getEndPoint())) {
                    priorityQueue.add(djToTest);
                    path.put(neighbor.getEndPoint(),
                            new SearchResultBestDuration(currentStation, djToTest.getTime(), getLineFromSegment(neighbor)));
                }
            }
        }
        return path;
    }

    public Map<Node, SearchResultBestDuration> dijkstra(Node startNode, Node endNode, int startTime) {
        return dijkstra(startNode, endNode, startTime, true, true);
    }

    // ================================== Dikjstra and it's auxiliary functions =======================================

    public MetroLine getLineFromSegment(Segment segment) {
        return segment instanceof SegmentMetro ? this.lines.get(((SegmentMetro) segment).getLine()) : null;
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
        String metroFile = "data/map_data.csv";
        String scheduleFile = "data/timetables.csv";
        try {
            Parser.parse(metroFile, scheduleFile);
        } catch (FileNotFoundException e) {
            System.out.println("File not found when parsing files " + e);
            return;
        } catch (IOException e) {
            System.out.println("IO error when parsing files " + e);
            return;
        }
        Set<StationDTO> stationsDTO = Parser.getStations(); // To be used for walk segments.
        Map<String, String> metroLinesTerminus = Parser.getMetroLines();
        stationsDTO.forEach(stationDTO -> {
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
                this.scheduleKeys.add(scheduleKey);
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

    // Adapters functions
    // -----------------------------------------------------------------------------------------------------------------
    Station stationDTOtoStation(StationDTO dto) { return new Station(dto.getName(), dto.getLatitude(), dto.getLongitude()); }

    // Djikstra classes.
    private static class DjikstraInfo implements Comparable<DjikstraInfo> {
        private final Node node;
        private int time;

        public DjikstraInfo(Node node, int time) {
            this.node = node;
            this.time = time;
        }

        public int getTime() { return time; }

        public void setTime(int time) { this.time = time; }

        public Node getNode() { return node; }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            DjikstraInfo that = (DjikstraInfo) o;
            return Objects.equals(node, that.node);
        }

        @Override
        public int hashCode() { return Objects.hash(node); }

        @Override
        public int compareTo(DjikstraInfo o) { return Integer.compare(time, o.time); }
    }
}
