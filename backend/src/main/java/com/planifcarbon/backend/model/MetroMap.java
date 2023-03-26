package com.planifcarbon.backend.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MetroMap {
    // I think graph needs to have all nodes & segments not only walk one.
    // Then to "remove" a segment for Dijkstra, we just give it Integer.MAX_VALUE as weight.
    private Map<Node, Set<Segment>> graph;
    private Set<MetroLine> lines;

    /**
     * {@summary Main constructor.}
     * 
     * @param graph graph of the metro map
     * @param lines lines of the metro map
     */
    public MetroMap() {
        graph = new HashMap<Node, Set<Segment>>();
        lines = new HashSet<MetroLine>();
    }

    /**
     * Return the first node with that name.
     * It can be null if no node with that name is found.
     * 
     * @param nodeName name of the node to get
     */
    public Node getNode(String nodeName) { return graph.keySet().stream().filter(n -> n.getName().equals(nodeName)).findFirst().get(); }

    /**
     * {@summary Return the list of nodes.}
     */
    public Set<Node> getNodes() { return graph.keySet(); }

    // Build functions --------------------------------------------------------------------------------------------------------------------
    /**
     * {@summary Add a new node to the graph.}
     */
    public void addNode(String name, double latitude, double longitude, Class<? extends Node> nodeClass) {
        try {
            Node node = nodeClass.getDeclaredConstructor().newInstance(name, latitude, longitude);
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
     * It will add the segment to both nodes connections lists.
     * 
     * @param segment segment to add
     */
    public void addSegment(String startNodeName, String endNodeName, double distance, double duration,
            Class<? extends Segment> segmentClass) {
        try {
            Segment segment = segmentClass.getDeclaredConstructor().newInstance(getNode(startNodeName), getNode(endNodeName), distance,
                    duration);
            graph.get(segment.getStartPoint()).add(segment);
            graph.get(segment.getEndPoint()).add(segment);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            System.out.println("Error while creating a new Segment " + e);
        }
    }

    /**
     * {@summary Create a new line &#38; fill it with Station that have the rigth name.}
     * 
     * @param id id of the line
     */
    public void createLine(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Set<Station> set = new HashSet<Station>();
        graph.values().stream().flatMap(s -> s.stream()) // stream over all segments
                .filter(s -> s instanceof SegmentMetro) // filter only SegmentMetro
                .filter(s -> ((SegmentMetro) s).getLine().equals(id)) // filter only SegmentMetro with the right line id
                .forEach(s -> { // add the start & end point of the segment to the set
                    set.add((Station) s.getStartPoint());
                    set.add((Station) s.getEndPoint());
                });
        // @formatter:on
        lines.add(new MetroLine(set, id));
    }

    // Main functions ---------------------------------------------------------------------------------------------------------------------
    public List<Node> routeCalculation(String startNodeName, String endNodeName) {
        // TODO get the 2 nodes from their names
        // TODO call Dijkstra
        // TODO return the list of nodes to go through
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
