package com.planifcarbon.backend.model;

import com.planifcarbon.backend.parser.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MetroMap} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MetroMap.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class MetroMapTest {

    /**
     * Generate a stream of arguments.
     *
     * @return a stream of arguments
     */
    static Stream<Arguments> generateDataNode() {
        return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class), Arguments.of("Maison", 1.0, 2.0,
                PersonalizedNode.class));
    }

    /**
     * Generate a stream of arguments.
     *
     * @return a stream of arguments
     */
    private static Stream<Arguments> generateDataSegmentList() {
        // @formatter:off
        return Stream.of(
                Arguments.of(0, "Avron", "Nation", true, true, List.of("Avron", "Nation")),
                Arguments.of(0, "Alexandre Dumas", "Nation", true, true, List.of("Alexandre Dumas", "Nation")),
                Arguments.of(0, "Alexandre Dumas", "Nation", true, false, List.of("Alexandre Dumas", "Avron", "Nation"
                )),
                Arguments.of(0, "Père Lachaise", "Nation", true, false, List.of("Père Lachaise", "Rue Saint-Maur",
                        "Parmentier",
                        "République", "Oberkampf", "Saint-Ambroise", "Voltaire", "Charonne", "Rue des Boulets",
                        "Nation")),
                Arguments.of(0, "Père Lachaise", "Nation", true, true, List.of("Père Lachaise", "Nation")),
                Arguments.of(35000, "Père Lachaise", "Nation", true, true, List.of("Père Lachaise", "Philippe Auguste",
                        "Alexandre Dumas", "Avron", "Nation")),
                Arguments.of(35000, "Gambetta", "Nation", true, true, List.of("Gambetta", "Père Lachaise", "Philippe " +
                                "Auguste",
                        "Alexandre Dumas", "Avron", "Nation")),
                Arguments.of(35000, "Gare de Lyon", "Gare du Nord", true, false, List.of("Gare de Lyon", "Bastille",
                        "Chemin Vert",
                        "Saint-Sébastien - Froissart", "Filles du Calvaire", "République", "Strasbourg - Saint-Denis"
                        , "Château d'Eau", "Gare de l'Est", "Gare du Nord")),

                Arguments.of(35000, new PersonalizedNode("A", 48.846408, 2.395640), "Nation", true, true, List.of("A"
                        , "Nation")),

                // From A to La Défense (Grande Arche)
                Arguments.of(35000, new PersonalizedNode("A", 48.846408, 2.395640), "La Défense (Grande Arche)", true
                        , true, List.of("A",
                                "Nation", "Rue des Boulets", "Charonne", "Voltaire", "Saint-Ambroise", "Oberkampf",
                                "R" +
                                        "épublique", "Strasbourg - Saint-Denis", "Bonne Nouvelle",
                                "Grands Boulevards", "Richelieu - Drouot", "Chaussée d'Antin - La Fayette", "Havre" +
                                        "-Caumartin"
                                , "Saint-Augustin", "Miromesnil", "Saint-Philippe du Roule",
                                "Franklin D. Roosevelt", "George V", "Charles de Gaulle - Etoile", "Argentine",
                                "Porte " +
                                        "Maillot", "Les Sablons", "Pont de Neuilly",
                                "Esplanade de la Défense", "La Défense (Grande Arche)")),

                // From A to B at time where line 9 is better than 1.
                Arguments.of(35000, new PersonalizedNode("A", 48.846408, 2.395640), new PersonalizedNode("B",
                        48.893216, 2.234292), true, true, List.of("A",
                        "Nation", "Rue des Boulets", "Charonne", "Voltaire", "Saint-Ambroise", "Oberkampf", "R" +
                                "épublique", "Strasbourg - Saint-Denis", "Bonne Nouvelle",
                        "Grands Boulevards", "Richelieu - Drouot", "Chaussée d'Antin - La Fayette", "Havre-Caumartin"
                        , "Saint-Augustin", "Miromesnil", "Saint-Philippe du Roule",
                        "Franklin D. Roosevelt", "George V", "Charles de Gaulle - Etoile", "Argentine", "Porte " +
                                "Maillot", "Les Sablons", "Pont de Neuilly",
                        "Esplanade de la Défense", "La Défense (Grande Arche)", "B")),

                // From A to B with different time where line 1 is better than 9.
                Arguments.of(36000, new PersonalizedNode("A", 48.846408, 2.395640), new PersonalizedNode("B",
                        48.893216, 2.234292), true, true, List.of("A",
                        "Nation", "Reuilly - Diderot", "Gare de Lyon", "Bastille", "Saint-Paul (Le Marais)", "Hôtel " +
                                "de Ville", "Châtelet", "Louvre - Rivoli",
                        "Palais Royal - Musée du Louvre", "Tuileries", "Concorde", "Champs-Élysées - Clemenceau",
                        "Franklin D. Roosevelt", "George V", "Charles de Gaulle - Etoile", "Argentine", "Porte " +
                                "Maillot", "Les Sablons", "Pont de Neuilly",
                        "Esplanade de la Défense", "La Défense (Grande Arche)", "B")),

                Arguments.of(35000, new PersonalizedNode("A", 48.846408, 2.395640), new PersonalizedNode("B",
                        48.909886, 2.336266), true, true, List.of("A",
                        "Nation", "Rue des Boulets", "Charonne", "Voltaire", "Saint-Ambroise", "Oberkampf", "R" +
                                "épublique", "Strasbourg - Saint-Denis", "Bonne Nouvelle",
                        "Grands Boulevards", "Richelieu - Drouot", "Chaussée d'Antin - La Fayette", "Havre-Caumartin"
                        , "Saint-Lazare", "Pont Cardinet", "Porte de Clichy",
                        "Saint-Ouen", "Mairie de Saint-Ouen", "B")),

                Arguments.of(35000, new PersonalizedNode("Maison", 48.8453461388873, 2.396355180412703),
                        new PersonalizedNode("Travail", 48.872641940207515, 2.303847587524504),
                        true, true, List.of("Maison", "Nation", "Reuilly - Diderot", "Gare de Lyon", "Bastille",
                                "Saint-Paul (Le Marais)", "Hôtel de Ville",
                                "Châtelet", "Louvre - Rivoli", "Palais Royal - Musée du Louvre", "Tuileries",
                                "Concorde", "Champs-Élysées - Clemenceau",
                                "Franklin D. Roosevelt", "George V", "Travail"))
        );
        // @formatter:on
    }

    /**
     * Tests the addNode method of the MetroMap class.
     *
     * @param name the name of the node to add
     * @param la the latitude of the node to add
     * @param lo the longitude of the node to add
     * @param cl the class of the node to add
     */
    @ParameterizedTest
    @MethodSource("generateDataNode")
    public void testAddNode(String name, double la, double lo, Class<? extends Node> cl) {
        MetroMap map = new MetroMap();
        map.addNode(name, la, lo, cl);
        assertEquals(1, map.getNodes().size());
    }

    /**
     * Tests the addNode method of the MetroMap class when an invalid node instance is provided.
     *
     * @param name the name of the node to add
     * @param la the latitude of the node to add
     * @param lo the longitude of the node to add
     */
    @ParameterizedTest
    @CsvSource({"A, 1.0, 2.0", "Maison, 1.0, 2.0"})
    public void testAddNodeThrows(String name, double la, double lo) {
        MetroMap map = new MetroMap();
        assertThrows(IllegalArgumentException.class, () -> map.addNode(name, la, lo, null));
    }

    /**
     * Tests the addSegmentMetro method of the MetroMap class.
     */
    @Test
    public void testAddSegmentMetro() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, "1");
        assertEquals(1, map.getSegments(new NodeForTest("A", 0.0, 0.0)).size());
    }

    /**
     * Tests that an exception is not thrown when adding a metro segment between two existing nodes.
     *
     * @throws IllegalArgumentException if the node is not in the graph or if there is already a segment between the two nodes
     */
    @Test
    public void testAddSegmentThrow() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        assertDoesNotThrow(() -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0,
                40000, "1"));
    }


    /**
     * Tests that an IllegalArgumentException is thrown when trying to add a segment with a null line name.
     *
     * @throws IllegalArgumentException if the line name is null
     */
    @Test
    public void testAddSegmentThrow2() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        assertThrows(IllegalArgumentException.class,
                () -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, null));
    }

    /**
     * Tests the {@link MetroMap#addSegmentWalk(Node, Node, double)} method to add a walking segment between two nodes.
     * Verifies that the segment is added successfully without throwing any exceptions.
     */
    @Test
    public void testAddSegmentWalk() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentWalk(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0);
    }


    /**
     * This test method is used to verify that the MetroMap object is properly initialized
     * and that the data contained in the map is correct. It uses a CSV source to provide
     * test parameters, which in this case is a station name and expected number of metro segments
     * and total segments.
     *
     @param stationName The name of the station to check the number of segments for
     @param nbSegmentsMetro The expected number of metro segments for the station
     @param nbSegments The expected total number of segments in the map
     */
    @ParameterizedTest
    @CsvSource({"Argentine, 4, 311"})
    public void testMetroMapDataCreation(String stationName, int nbSegmentsMetro, int nbSegments) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());
        assertNotEquals(0, map.getLines().size());
        assertNotEquals(0, map.getStations().size());
        assertNotEquals(0, map.getNodes().size());
        assertEquals(93, map.getLines().size());
        assertEquals(308, map.getStations().size());
        assertEquals(308, map.getNodes().size());
        List<Station> stations = new ArrayList<>();
        map.getStations().values().forEach(station -> {
            assertFalse(stations.contains(station));
            stations.add(station);
        });
        List<MetroLine> metroLines = new ArrayList<>();
        map.getLines().values().forEach(line -> {
            assertFalse(metroLines.contains(line));
            assertNotNull(line.getSchedules());
            metroLines.add(line);
        });
        assertEquals(nbSegmentsMetro, map.getSegmentsMetro(new NodeForTest(stationName, 0.0, 0.0)).size());
    }


    /**
     * Tests the functionality of the {@code getStationByName} method in the {@code MetroMap} class, which retrieves a
     *
     * {@code Station} object from the {@code Map} of stations based on its name.
     * @param stationName the name of the station to retrieve
     */
    @ParameterizedTest
    @ValueSource(strings = {"Argentine"})
    public void testGetStationByName(String stationName) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station station = map.getStationByName(stationName);
        assertNotNull(map.getStationByName(stationName));
        assertNotEquals(0, station.getSchedules().size());
        assertEquals(4, station.getSchedules().size());
    }

    /**
     * Tests the getSegmentsFromPath method of the MetroMap class.
     *
     * @param timeStart the starting time for the path
     * @param start the starting node or station for the path
     * @param end the ending node or station for the path
     * @param metro a boolean indicating whether or not to include metro segments in the path
     * @param walk a boolean indicating whether or not to include walking segments in the path
     * @param expected a list of expected node names in the path
     */
    @ParameterizedTest
    @MethodSource("generateDataSegmentList")
    public void testGetSegmentsFromPath(int timeStart, Object start, Object end, boolean metro, boolean walk,
                                        List<String> expected) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Node startNode;
        if (start instanceof String) {
            startNode = map.getStationByName((String) start);
        } else {
            startNode = (Node) start;
        }
        Node endNode;
        if (end instanceof String) {
            endNode = map.getStationByName((String) end);
        } else {
            endNode = (Node) end;
        }

        List<DataSegment> segments = map.getSegmentsFromPath(startNode, endNode, timeStart, metro, walk, true);
        assertEquals(expected.size() - 1, segments.size());
        for (int i = 0; i < segments.size(); i++) {
            assertEquals(expected.get(i), segments.get(i).getNodeStart().getName());
            assertEquals(expected.get(i + 1), segments.get(i).getNodeEnd().getName());
        }
    }

    /**
     * This method tests the ability of the MetroMap class to get the best path with a personalized node.
     */
    @Test
    public void testPathWithPersonalizedNode() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        List<DataSegment> result = map.getSegmentsFromPath(new Station("Bercy", 48.84014763512746, 2.3791909087742877),
                new PersonalizedNode("CUSTOM", 48.86924811771283, 2.274952380371076), 79200, true, true, true);
        assertNotNull(result);
    }

    /**
     * Tests the availability of all the necessary information.
     */
    @Test
    public void testGetTotalTable() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());
    }

    /**
     * Tests if an {@link IllegalArgumentException} is thrown when the start node is null.
     */
    @Test
    public void testStartNodeIsNull() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertThrows(IllegalArgumentException.class, () -> map.dijkstra(null, null, 10000, true, true, true));
    }

    /**
     * Tests if an {@link IllegalArgumentException} is thrown when the time provided is negative.
     */
    @Test
    public void testTimeIsNegative() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertThrows(IllegalArgumentException.class, () -> map.dijkstra(new NodeForTest("", 1, 1), new NodeForTest(""
                , 1, 1), -10000, true, true, true));
    }
}
