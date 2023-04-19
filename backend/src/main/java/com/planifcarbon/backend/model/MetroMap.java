package com.planifcarbon.backend.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.*;
import org.springframework.stereotype.Component;
import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.StationDTO;
import com.planifcarbon.backend.parser.Parser;
import jakarta.annotation.PostConstruct;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.concurrent.ConcurrentHashMap;

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
    /** Structure to collect pairs (Node from, Node to) like a keys,
     *  and pairs (int departTime, int arriveTime) like a values. */
    private TreeMap<KeyTotalTable, List<TimeValue>> totalTable;

    /**
     * {@summary Main constructor.}
     */
    public MetroMap() {
        graph = new HashMap<Node, Set<Segment>>();
        lines = new HashMap<String, MetroLine>();
        stations = new HashMap<String, Station>();
        scheduleKeys = new HashSet<ScheduleKey>();
        totalTable = new TreeMap<KeyTotalTable, List<TimeValue>>(new Comparator<KeyTotalTable>() {
            @Override
            public int compare(KeyTotalTable k1, KeyTotalTable k2) {
                int cmp1 = k1.nodeFrom.getName().compareTo(k2.nodeFrom.getName());
                int cmp2 = k1.nodeTo.getName().compareTo(k2.nodeTo.getName());
                if ((cmp1 == 0) && (cmp2 == 0))
                {   //System.out.println(">> compare K1{" + k1.nodeFrom.getName() + ":" + k1.nodeTo.getName()
                    //                   + "} == K2{" + k2.nodeFrom.getName() + ":" + k2.nodeTo.getName() + "}"
                    //                  );
                    return 0;
                }
                if (cmp1 > 0) return 1;
                if ((cmp1 == 0) && (cmp2 > 0)) return 1;
                return -1;
            }
        });
    }

    /**
     * {@summary Class of pairs (Node from, Node to) to use in totalTable like a key.}
     */
    private class KeyTotalTable {
        public Node nodeFrom;
        public Node nodeTo;
        private KeyTotalTable(Node start, Node finish) {
            nodeFrom = start;
            nodeTo = finish;
        }
        public boolean isTheSameKey(Node first, Node second) {
            if (first.equals(nodeFrom) && second.equals(nodeTo)) { return true; }
            return false;
        }
    }
    /**
     * {@summary Class of pairs (int departTime, int arriveTime) to use in totalTable like a values.}
     */
    private class TimeValue {
        public int departTime;
        public int arriveTime;
        private TimeValue(int start, int finish) {
            departTime = start;
            arriveTime = finish;
        }
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

    public Map<KeyTotalTable, List<TimeValue>> getTotalTable() { return totalTable; }


    /**
     * {@summary Return the list of segments.}
     * @return the list of segments
     */
    public Set<Segment> getSegments(Node node) { return graph.get(node); }
    public Set<Segment> getSegmentsMetro(Node node) {
        return graph.get(node).stream().filter(segment -> segment instanceof SegmentMetro).collect(HashSet::new, HashSet::add,
                HashSet::addAll);
    }

    /**
     * {@summary Fing scheduleKey (final station of matro line) by name.}
     * @return the scheduleKey (final station of matro line)
     */
    public ScheduleKey getScheduleKeyByName(String name) {
        List <ScheduleKey> ret = this.scheduleKeys.stream()
                .filter(sh -> (sh.getTerminusStation().getName()).equals(name))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return ret.get(0);
    }

    // ==================================  Dikjstra and it's auxiliary functions =======================================

    /**
     * {@summary Tool for stream usage. Helps to find unique values from stream. That migth be necessary if
     * need to create new map and the keys for it have duplicate.}
     * Source : https://stackoverflow.com/questions/59211179/stream-api-distinct-by-name-and-max-by-value
     * @param keyExtractor function which will play the role of filter.
     * @return predicate to apply in stream.
     */
  //  private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
  //      Set<Object> seen = ConcurrentHashMap.newKeySet();
  //      return t -> seen.add(keyExtractor.apply(t));
  //  }

    /**
     * {@summary Finds all nodes-neighbours of given node and get weights (expressed in form of distances)
     * of segments connected with whem.}
     * @param currentNode current node
     * @return neighbDistances map made up of neighboring nodes and distances to them
     */
  //  private Map <Node, Double> getNeighboursDistances (Node currentNode) {
  //      if (null == currentNode) {
  //          throw new IllegalArgumentException("input should not be null");
  //      }
  //      Map <Node, Double> neighbDistances = graph.get(currentNode)   // Set
  //              .stream()
  //              .filter(distinctByKey(b -> b.getEndPoint()))
  //              .collect(Collectors.toMap(segment -> segment.getEndPoint(), segment -> segment.getDistance()));
  //      return neighbDistances;
  //  }

    /**
     * {@summary Finds all nodes-neighbours of given node and get weights (expressed in form of durations of trip)
     * of segments connected with whem.}
     * @param currentNode current node
     * @return neighbDurations map made up of neighboring nodes and durations to them
     */
   // private Map <Node, Integer> getNeighboursDurations (Node currentNode) {
   //     if (null == currentNode) {
   //         throw new IllegalArgumentException("input should not be null");
   //     }
   //     Map <Node, Integer> neighbDurations = graph.get(currentNode)   // Set
   //             .stream()
   //             .filter(distinctByKey(b -> b.getEndPoint()))
   //             .collect(Collectors.toMap(segment -> segment.getEndPoint(), segment -> segment.getDuration()));
   //     return neighbDurations;
   // }

   // private boolean notAllVisited(Map<Node, Boolean> visited)
   // {
   //     return visited.values().stream().anyMatch(Boolean.FALSE::equals);
   // }

    /**
     * {@summary Obtain all neighbour stations of given node.}
     * @param startNode node from which Dikjstra will be launched
     * @param startTime time of starting the trip
     * @return the map of pairs of nodes (Child, Parent) which represent the path of most optimized by time
     */
    private List<Station> getNeighbours (Node currentNode) {
        if (null == currentNode) {
            throw new IllegalArgumentException("input should not be null");
        }
        List<Station> neighbs = graph.get(currentNode)
                .stream()
                .map(segment -> segment.getEndPoint())
                .map(node -> getStationByName(node.getName()))
                .collect(Collectors.toList());
        return neighbs;
    }


    /**
     * {@summary Finds the nearest trains departing from the given station after given time.}
     * @param arrivalTime time after which need to find nearest trains.
     * @param currentStation station for which need to find nearest trains.
     * @return map contains ending stations (key) and arrival time on given station (value).
     */
    private Map.Entry<MetroLine, Integer> getNearestDepartureTime(int arrivalTime, Station currentStation) {
        if (null == currentStation) {
            throw new IllegalArgumentException("input should not be null");
        }
     //   if (arrivalTime < 0) {                                              // AZH with that tests doesn't pass !!!! Somebody have negative time
     //       throw new IllegalArgumentException("time has to be positive");
     //   }
        // find all ScheduleKey for currentStation
        Map<ScheduleKey, Integer> sh = currentStation.getSchedules();

        Map<MetroLine, Integer> bestOpportunities = new HashMap<MetroLine, Integer>();

        // for ScheduleKey find the first time schedule witch will be > that arrivalTime (i.e. nearest train for each ScheduleKey)
        sh.forEach((scheduleKey, value) ->  {
            List<Integer> times = scheduleKey.getMetroLine().getSchedules();
            int i = 0;
            while(arrivalTime > (times.get(i) + value)) {
                i++;
            }
            bestOpportunities.put(scheduleKey.getMetroLine(), times.get(i) + value);
        });

        // between them chose the min one
        int min = Integer.MAX_VALUE;

        for (Map.Entry<MetroLine, Integer> entry : bestOpportunities.entrySet()) {
            if (entry.getValue() < min)
            {
                min = entry.getValue();
                return entry;
            }
        }
        System.out.println("Nearest Departure Time not found");
        return null;
    }

    /**
     * {@summary Implementation of Dikjstra algorithm.}
     * @param startNode node from which Dikjstra will be launched
     * @param startTime time of starting the trip
     * @return the map of pairs of nodes (Node Child, Node Parent) which represent the path of most optimized by time
     */
    public Map<Node, Node> Dijkstra(Node startNode, int startTime)
    {
        Set<KeyTotalTable> keysMap = this.totalTable.keySet();
        int cccc = 0;
        for (var el: keysMap)
        {
            if (null == this.totalTable.get(el))
            {
                System.out.println(">> LIST IS NULL {" + el.nodeFrom.getName() + ":" + el.nodeTo.getName() + "}");
                cccc++;
            }
        }
        if (cccc != 0)  while(true){}

        if (null == startNode) {
            throw new IllegalArgumentException("input should not be null");
        }
        if (startTime < 0) {
            throw new IllegalArgumentException("time has to be positive");
        }

        KeyTotalTable key = new KeyTotalTable(null, null);
        // ============ 0. Create returned structure ===================================================================
        Map<Node, Node> parents = new HashMap<Node, Node>();

        // ============ 1. Set initial weight for all vertex = ꚙ ======================================================
        Map<Node, Integer> weightNodes = new HashMap<Node, Integer>();
        Set<Node> allNodes = getNodes();
        allNodes.forEach(node -> {
            weightNodes.put(node, Integer.MAX_VALUE);
        });

        // =========== 2. Create structure of vertex (let’s call it ‘parens’), which size = nb of vertex ===============
        allNodes.forEach(node -> {
            parents.put(node, startNode);
        });
        parents.replace(startNode, null);       // startNode has no parent, if not -> loop

        // =========== 3. Create and init structure of visited vertex ==================================================
        Map<Node, Boolean> visited = new HashMap<Node, Boolean>();
        allNodes.forEach(node -> {
            visited.put(node, new Boolean(false));
        });

        // ================== 4. Prepare all for startStation ==========================================================
        Station startStation = getStationByName(startNode.getName());

        // --------- find departure time from start station ------------------------------------------------------------
        Map.Entry<MetroLine, Integer> nearestTrain = getNearestDepartureTime(startTime, startStation);
        Integer departureTime = nearestTrain.getValue();

        // ------------  put  weight for start node --------------------------------------------------------------------
        weightNodes.replace(startNode, departureTime);
        System.out.println("Departure at time = " + departureTime);

        // ============= 5. Create priorityQueue where will be stocked pairs (Station, time) ===========================
        PriorityQueue<Map.Entry<Station, Integer>> priorityQueue = new PriorityQueue<>(
                Comparator.<Map.Entry<Station, Integer>>comparingInt(Map.Entry::getValue));

        // ----------------- add start station -------------------------------------------------------------------------
        priorityQueue.add(new AbstractMap.SimpleEntry(startStation, departureTime));

        // ================= 6. Graph traversal ========================================================================
        while( priorityQueue.size() != 0 ) {
            // obtain the minimal weight and it's station to work with
            Map.Entry<Station, Integer> current = priorityQueue.poll();
            int currentTime = current.getValue().intValue();           // minimal time
            Station currentStation = current.getKey();          // it's station

            if (visited.get(currentStation) == true) {
                continue;
            }

            List<Station> getNeibs = getNeighbours(currentStation);              // neib stations of current station

            visited.replace(currentStation, new Boolean(true));

            int min = Integer.MAX_VALUE;
            Station nextStation = currentStation;  // hack: being a key of map it cannot be initialized here by null

            // find all segments with all neighbours where currentStation is startStation and neighbour is endStation
            //      for that use totalTable with key (currentNode, neibNode)
            //      to find values (departTime, arriveTime)
            for (Station neib: getNeibs) {
                // take all (departTime, arriveTime) for this (currentNode, neibNode)
                key.nodeFrom = currentStation;
                key.nodeTo = neib;
                List<TimeValue> listDepTimeArrTime = this.totalTable.get(key);      // AZH here return null for
                                                                                    // existing key and value in TotalTable (jump to AZH1)
                                                                                    // - PROBLEM !!!
             //  if (listDepTimeArrTime == null)
             //  {
             //      System.out.println(">> LIST IS NULL {" + key.nodeFrom.getName() + ":" +
             //              key.nodeTo.getName() + "} map size is " + this.totalTable.size() + " Hash "+ System.identityHashCode(this.totalTable));
             //      while (true) {}
             //  }
             //  else
             //  {
             //      System.out.println(">> LIST Found {" + key.nodeFrom.getName() + ":" + key.nodeTo.getName() + "} map size is " + this.totalTable.size());
             //  }

             //  System.out.println(Arrays.toString(listDepTimeArrTime.toArray()));

                // find minimal time between them
                for (int i = 0; i < listDepTimeArrTime.size(); i++) {
                    if (listDepTimeArrTime.get(i).departTime == currentTime) {      // check only pairs with depart = current time
                        if (listDepTimeArrTime.get(i).arriveTime < min) {
                            System.out.println(" <<<<<<<<<<<<<<<<<<< HERE <<<<<<<<<<<<<<<<<<<<<<");
                            min = listDepTimeArrTime.get(i).arriveTime;
                            nextStation = neib;
                        }

                    }
                }
                currentTime = min;
                parents.replace(nextStation, currentStation);
                priorityQueue.add(new AbstractMap.SimpleEntry(nextStation, min));
            }

        }
        return parents;
    }

    // ================================== End Dikjstra and it's auxiliary functions ====================================

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

     //   addAllWalkSegments(getAllStations());
        calculateTimeTableForStations();
        fillTotalTable();

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

    /**
     * {@summary Function to fill structure Map<ScheduleKey, List<Integer>> timeTable in the class Station
     * it's made for all stations of each metro line.
     */
    private void calculateTimeTableForStations() {
        lines.forEach((name, metroLine) -> {   // for each line
            Set<Station> tmpStations = metroLine.getStations();         // all stations of line
            List<Integer> lineSchedules = metroLine.getSchedules();     // scheduls of line

            tmpStations.forEach(currStation -> {          // for each station ...
                Map<ScheduleKey, Integer> stationSchedules = currStation.getSchedules();   // time of trip from terminus to currStation

                stationSchedules.forEach((scheduleKey, value) -> {           // ... from each terminus
                    List<Integer> listSched = new LinkedList<Integer>();
                    for (int i = 0; i < lineSchedules.size(); i++) {
                        listSched.add(value.intValue() + lineSchedules.get(i).intValue());      // create time table
                    }
                    listSched = listSched.stream().sorted().collect(Collectors.toList());       // sort
                    currStation.addTimeToTimeTable(scheduleKey, listSched);                     // add to class
                });
            });
        });
    }

    private void fillTotalTable()
    {
        Set<Node> allNodes = this.getNodes();

        for (Node node : allNodes)
        {
            if (node instanceof Station)
            {
                Station startStation = getStationByName(node.getName());
                Set<Segment> herSegments = getSegments(node);

                // take all trains of this station
                Collection<Integer> herSchedules = new ArrayList<Integer>();
                Map<ScheduleKey, List<Integer>> timeTable = startStation.getTimeTable();
                timeTable.forEach((keyShed, listShed) -> {
                    herSchedules.addAll(listShed);
                });


                for (Segment segm : herSegments)
                {
                    if (segm.getEndPoint() instanceof Station)
                    {
                        Node neibStation = segm.getEndPoint();
                        KeyTotalTable newKey = new KeyTotalTable(startStation, neibStation);

                        boolean isNewValue = false;
                        List<TimeValue> newValue = this.totalTable.get(newKey);
                        if (null == newValue)
                        {
                            isNewValue = true;
                            newValue = new ArrayList<TimeValue>();
                        }
                        else
                        {
                            //System.out.println("++ key duplicate {" + newKey.nodeFrom.getName() + ":" + newKey.nodeTo.getName()
                            //        + "} TimeTableSize " + newValue.size()+ " Hash "+ System.identityHashCode(this.totalTable));
                        }
                        //else
                        //{
                        //    System.out.println(">> AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        //    while (true) {}
                        //}

                        for (Integer el : herSchedules)
                        {
                            newValue.add(new TimeValue(el.intValue(), el.intValue() + segm.getDuration()));
                        }

                        //AZH: TODO: check for duplicates
                        //herSchedules.forEach(departShed -> {
                        //    newValue.add(new TimeValue(departShed.intValue(), departShed.intValue() + segm.getDuration()));
                        //});
                       // newValue.forEach(el -> {
                       //     if ((node.equals(getStationByName("Duroc")) && neibStation.equals(getStationByName("Saint-François-Xavier"))))
                       //     {
                       //         System.out.print(" [");
                       //         System.out.print(el.departTime + " - " + el.arriveTime);
                       //         System.out.print("], ");
                       //     }
                       //
                       //  });

                        if (isNewValue)
                        {
                            //System.out.println(">> Add key {" + newKey.nodeFrom.getName() + ":" + newKey.nodeTo.getName()
                            //        + "} TimeTableSize " + newValue.size()+ " Hash "+ System.identityHashCode(this.totalTable));
                            this.totalTable.put(newKey, newValue);
                            //if (null == this.totalTable.get(newKey))
                            //{
                            //    System.out.println(">> PANIC");
                            //    while(true){}
                            //
                            //}
                        }
                    }
                }
            }
        }

        //Set<KeyTotalTable> keysMap = this.totalTable.keySet();
        //int cccc = 0;
        //for (var el: keysMap)
        //{
        //    if (null == this.totalTable.get(el))
        //    {
        //        System.out.println(">> LIST IS NULL {" + el.nodeFrom.getName() + ":" + el.nodeTo.getName() + "}");
        //        cccc++;
        //    }
        //    else
        //    {
        //        System.out.println(">> Key in place {" + el.nodeFrom.getName() + ":" + el.nodeTo.getName() + "}");
        //    }
        //}
        //if (cccc != 0)
        //{
        //    System.out.println(">> Missing " + cccc + " Keys count " + keysMap.size() + " Map size " + this.totalTable.size());
        //    while(true){}
        //}
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
