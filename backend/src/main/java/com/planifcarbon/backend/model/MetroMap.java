package com.planifcarbon.backend.model;

import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.parser.Parser;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {Represents the metro map.}
 */
@Component
public final class MetroMap {
    private final Map<Node, Set<Segment>> graph;
    private final Map<String, MetroLine> lines;
    private final Map<String, Station> stations;
    /** Set of all final stations stations (mostly used in tests). */
    private Set<ScheduleKey> scheduleKeys;

    /**
     * {Main constructor.}
     */
    public MetroMap() {
        graph = new HashMap<Node, Set<Segment>>();
        lines = new HashMap<String, MetroLine>();
        stations = new HashMap<String, Station>();
        scheduleKeys = new HashSet<ScheduleKey>();
    }

    /**
     * Returns a map of all stations in the network, keyed by their names.
     *
     * @return A map of all stations in the network.
     */
    public Map<String, Station> getStations() {
        return stations;
    }

    /**
     * Returns a set of all stations in the network.
     *
     * @return A set of all stations in the network.
     */
    public Set<Station> getAllStations() {
        return getStations().values().stream().collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    /**
     * Returns a map of all lines in the network, keyed by their names.
     *
     * @return A map of all lines in the network.
     */
    public Map<String, MetroLine> getLines() {
        return lines;
    }

    /**
     * Returns the graph of the network, represented as a map from nodes to sets of segments.
     *
     * @return The graph of the network.
     */
    public Map<Node, Set<Segment>> getGraph() {
        return graph;
    }

    /**
     * Returns the station in the network with the given name, or null if there is no such station.
     *
     * @param stationName The name of the station to retrieve.
     * @return The station with the given name, or null if there is no such station.
     */
    public Station getStationByName(String stationName) {
        return this.stations.getOrDefault(stationName, null);
    }

    /**
     * Get the nodes from dijkstra graphs.
     *
     * @return the list of nodes.
     */
    public Set<Node> getNodes() {
        return graph.keySet();
    }

    /**
     * {Return the list of segments.}
     *
     * @param node the node to get its segments.
     * @return the list of segments
     */
    public Set<Segment> getSegments(Node node) {
        return graph.get(node);
    }

    /**
     * Get set of metro segments.
     *
     * @param node to have all his segments.
     * @return set of metro segments for the node.
     */
    public Set<Segment> getSegmentsMetro(Node node) {
        return graph.get(node).stream().filter(segment -> segment instanceof SegmentMetro).collect(Collectors.toSet());
    }

    /**
     * {Find scheduleKey (final station of matro line) by name.}
     *
     * @param name name of terminus station to get schedule.
     * @return the scheduleKey (final station of matro line)
     */
    public ScheduleKey getScheduleKeyByName(String name) {
        List<ScheduleKey> ret = this.scheduleKeys.stream()
                .filter(sh -> (sh.getTerminusStation().getName()).equals(name))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ret.get(0);
    }

    /**
     * {Finds the nearest trains departing from the given station after given time.}
     *
     * @param arrivalTime time after which need to find nearest trains.
     * @param currentStation station for which need to find nearest trains.
     * @param lineName metro line.
     * @return minimal arrival time on given station for the given line.
     */
    private int getNearestDepartureTime(int arrivalTime, Station currentStation, String lineName) {
        if (null == currentStation) {
            throw new IllegalArgumentException("input should not be null");
        }
        MetroLine line = this.lines.get(lineName);
        int duration = currentStation.getScheduleForKey(new ScheduleKey(line.getTerminusStation(), line));
        return line.getSchedules()
                .stream()
                .filter((departureTime) -> departureTime + duration >= arrivalTime)
                .findFirst()
                .map((departureTime) -> departureTime + duration)
                .orElse(-1);
    }

    /**
     * {Implementation of Dikjstra algorithm.}
     *
     * @param startNode node from which Dikjstra will be launched
     * @param endNode node where we are going.
     * @param startTime time for launching the dijkstra.
     * @param metro if (true) include metro segments in the search.
     * @param walk if (true) include walk segments in the search.
     * @return the map of pairs of nodes (Node Child, Node Parent) which represent the path of most optimized.
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
        // Add start and end nodes if they are personalized
        if (startNode instanceof PersonalizedNode || endNode instanceof PersonalizedNode) {
            allNodes = new HashSet<Node>(allNodes); // make set mutable
        }
        if (startNode instanceof PersonalizedNode && !allNodes.contains(startNode)) {
            allNodes.add(startNode);
        }
        if (endNode instanceof PersonalizedNode && !allNodes.contains(endNode)) {
            allNodes.add(endNode);
        }

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
        PriorityQueue<DjikstraInfo> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(DjikstraInfo::getWeight));

        // ----------------- add start station -------------------------------------------------------------------------
        priorityQueue.add(new DjikstraInfo(startNode, startTime));

        // ================= 6. Graph traversal ========================================================================

        while (!priorityQueue.isEmpty()) {
            DjikstraInfo current = priorityQueue.poll();

            int currentTime = current.getWeight();
            Node currentNode = current.getNode();

            visited.replace(currentNode, true);

            if (currentNode.equals(endNode)) {
                return path;
            }

            Set<Segment> neighbors;
            if (startNode.equals(currentNode) && startNode instanceof PersonalizedNode) {
                neighbors = new HashSet<Segment>();
                for (Node node : allNodes) {
                    if (!node.equals(startNode)) {
                        neighbors.add(new SegmentWalk(startNode, node));
                    }
                }
            } else if (endNode.equals(currentNode) && endNode instanceof PersonalizedNode) {
                neighbors = new HashSet<Segment>();
                for (Node node : allNodes) {
                    if (!node.equals(endNode)) {
                        neighbors.add(new SegmentWalk(endNode, node));
                    }
                }
            } else {
                neighbors = new HashSet<Segment>(this.getSegments(currentNode)); // copy of set
            }
            // Add personalized nodes segments if start and end node is personalized
            if (startNode instanceof PersonalizedNode && !startNode.equals(currentNode)) {
                neighbors.add(new SegmentWalk(currentNode, startNode));
            }
            if (endNode instanceof PersonalizedNode && !endNode.equals(currentNode)) {
                neighbors.add(new SegmentWalk(currentNode, endNode));
            }

            // Remove segments if only metro or only walk is allowed
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
                        djikstraInfo.get().setWeight(djToTest.getWeight());
                        priorityQueue.remove(djikstraInfo.get());
                        priorityQueue.add(djikstraInfo.get());
                        path.replace(neighbor.getEndPoint(),
                                new SearchResultBestDuration(currentNode, djToTest.getWeight(), getLineFromSegment(neighbor)));
                    }
                } else if (!visited.get(neighbor.getEndPoint())) {
                    priorityQueue.add(djToTest);
                    path.put(neighbor.getEndPoint(),
                            new SearchResultBestDuration(currentNode, djToTest.getWeight(), getLineFromSegment(neighbor)));
                }
            }
        }
        return path;
    }

    /**
     * Get the shortest path between two nodes with dijkstra algorithm and return the segments of the path
     *
     * @param startNode the start node
     * @param endNode   the end node
     * @param startTime the start time
     * @param metro     if metro segments are allowed
     * @param walk      if walk segments are allowed
     * @return a list of segments data easy to use
     */
    public List<DataSegment> getSegmentsFromPath(Node startNode, Node endNode, int startTime, boolean metro, boolean walk) {
        Map<Node, SearchResultBestDuration> map = dijkstra(startNode, endNode, startTime, metro, walk);

        LinkedList<DataSegment> segments = new LinkedList<DataSegment>();
        Node current = endNode;
        int departureTime = startTime;

        while (!current.equals(startNode) && map.get(current) != null) {
            Node next = map.get(current).getNodeDestination();
            int arrivalTime = map.get(current).getArrivalTime();
            segments.addFirst(new DataSegment(next, current, arrivalTime, departureTime, map.get(current).getMetroLine(), 0));
            departureTime = arrivalTime;
            current = next;
        }

        return segments;
    }

    // ================================== Dikjstra and it's auxiliary functions =======================================

    /**
     * @param segment from which we want to get Metro Line.
     * @return metro line for a given segment.
     */
    public MetroLine getLineFromSegment(Segment segment) {
        return segment instanceof SegmentMetro ? this.lines.get(((SegmentMetro) segment).getLine()) : null;
    }

    // Build functions
    // --------------------------------------------------------------------------------------------------------------------
    /**
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
        Set<NodeDTO> stationsDTO = Parser.getStations(); // To be used for walk segments.
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
     *
     * @param segmentMetroDTOS metro segments given in the CSV file.
     * @param metroLines map of line and its stations from CSV file.
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
     *
     * @param metroLines map of line and its stations from CSV file.
     * @param metroLinesTerminus terminus station for each metro line from CSV file.
     * @param schedules each metro line and its schedules.
     */
    private void setMetroLineSchedules(Map<String, Set<Station>> metroLines, Map<String, String> metroLinesTerminus,
                                       Map<String, List<Integer>> schedules) {
        metroLines.forEach((key, value) -> {
            List<Integer> schedule = schedules.getOrDefault(key, new ArrayList<>());
            this.lines.put(key, new MetroLine(key, value, schedule, this.stations.get(metroLinesTerminus.get(key))));
        });
    }

    /**
     * Calculate time for any train to get to a station from terminus in each metro line.
     *
     * @param metroLinesTerminus terminus station for each metro line from CSV file.
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
     *
     * @param stations all metro stations in the given network.
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
     * Add a new node to the graph.
     *
     * @param name node name.
     * @param latitude node's latitude.
     * @param longitude node's longitude.
     * @param nodeClass type of node to create.
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
            throw new IllegalArgumentException("Error while creating a new node " + e);
        }
    }

    /**
     * Add a new segment to the graph.
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
     * Add a new segment to the graph.
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
     * Add a new segment to the graph.
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

    /**
     * Transform station dto object to station.
     *
     * @param dto data transfer object to transform to a station.
     * @return station from the node dto.
     */
    Station stationDTOtoStation(NodeDTO dto) {
        return new Station(dto.getName(), dto.getLatitude(), dto.getLongitude());
    }

    /**
     * @Summary Class which is used to simulate dijkstra nodes.
     */
    // Djikstra classes.
    private static class DjikstraInfo implements Comparable<DjikstraInfo> {
        private final Node node;
        private int weight;

        /**
         * Constructs a new `DjikstraInfo` instance with the given node and weight.
         *
         * @param node the node represented by this instance
         * @param weight the distance from the source node to this node
         */
        public DjikstraInfo(Node node, int weight) {
            this.node = node;
            this.weight = weight;
        }

        /**
         * Gets the distance from the source node to this node.
         *
         * @return the distance from the source node to this node
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Sets the distance from the source node to this node.
         *
         * @param weight the new distance from the source node to this node
         */
        public void setWeight(int weight) {
            this.weight = weight;
        }

        /**
         * Gets the node represented by this instance.
         *
         * @return the node represented by this instance
         */
        public Node getNode() {
            return node;
        }

        /**
         * Checks whether this instance is equal to the specified object.
         *
         * @param o the object to compare to
         * @return true if this instance is equal to the specified object, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DjikstraInfo that = (DjikstraInfo) o;
            return Objects.equals(node, that.node);
        }

        /**
         * Computes the hash code for this instance.
         *
         * @return the hash code for this instance
         */
        @Override
        public int hashCode() {
            return Objects.hash(node);
        }

        /**
         * Compares this instance to the specified `DjikstraInfo` instance based on their weights.
         *
         * @param o the `DjikstraInfo` instance to compare to
         * @return a negative integer, zero, or a positive integer as this instance is less than, equal to, or greater than the specified instance
         */
        @Override
        public int compareTo(DjikstraInfo o) {
            return Integer.compare(weight, o.weight);
        }
    }
}
