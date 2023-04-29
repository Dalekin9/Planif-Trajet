package com.planifcarbon.backend.model;

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


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MetroMap.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class MetroMapTest {

    static Stream<Arguments> generateDataNode() {
        return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class), Arguments.of("Maison", 1.0, 2.0,
                PersonalizedNode.class));
    }

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

    @ParameterizedTest
    @MethodSource("generateDataNode")
    public void testAddNode(String name, double la, double lo, Class<? extends Node> cl) {
        MetroMap map = new MetroMap();
        map.addNode(name, la, lo, cl);
        assertEquals(1, map.getNodes().size());
    }

    @ParameterizedTest
    @CsvSource({"A, 1.0, 2.0", "Maison, 1.0, 2.0"})
    public void testAddNodeThrows(String name, double la, double lo) {
        MetroMap map = new MetroMap();
        assertThrows(IllegalArgumentException.class, () -> map.addNode(name, la, lo, null));
    }

    @Test
    public void testAddSegmentMetro() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, "1");
        assertEquals(1, map.getSegments(new NodeForTest("A", 0.0, 0.0)).size());
    }

    @Test
    public void testAddSegmentThrow() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        assertDoesNotThrow(() -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0,
                40000, "1"));
    }

    @Test
    public void testAddSegmentThrow2() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        assertThrows(IllegalArgumentException.class,
                () -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, null));
    }

    @Test
    public void testAddSegmentWalk() {
        MetroMap map = new MetroMap();
        map.addNode("A", 1.0, 2.0, Station.class);
        map.addNode("B", -1.0, 10.0, Station.class);
        map.addSegmentWalk(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0);
    }

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
        // assertEquals(nbSegments, map.getSegments(new NodeForTest(stationName, 0.0, 0.0)).size());
    }

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

    @ParameterizedTest
    @ValueSource(strings = {"Gare d'Austerlitz"})
    public void testgetScheduleKeyByName(String name) {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getScheduleKeyByName(name));
        ScheduleKey station = map.getScheduleKeyByName(name);
        assertNotNull(station.getTerminusStation());
        assertNotNull(station.getMetroLine());
    }

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

        List<DataSegment> segments = map.getSegmentsFromPath(startNode, endNode, timeStart, metro, walk);
        assertEquals(expected.size() - 1, segments.size());
        for (int i = 0; i < segments.size(); i++) {
            assertEquals(expected.get(i), segments.get(i).getNodeStart().getName());
            assertEquals(expected.get(i + 1), segments.get(i).getNodeEnd().getName());
        }
    }

    @Test
    public void testPathWithPersonalizedNode() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        List<DataSegment> result = map.getSegmentsFromPath(new Station("Bercy", 48.84014763512746, 2.3791909087742877),
                new PersonalizedNode("CUSTOM", 48.86924811771283, 2.274952380371076), 79200, true, true);
        assertNotNull(result);
    }

    @Test
    public void testGetTotalTable() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertNotNull(map.getLines());
        assertNotNull(map.getStations());
        assertNotNull(map.getGraph());
    }

    @Test
    public void testStartNodeIsNull() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertThrows(IllegalArgumentException.class, () -> map.dijkstra(null, null, 10000, true, true));
    }

    @Test
    public void testTimeIsNegative() {
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        assertThrows(IllegalArgumentException.class, () -> map.dijkstra(new NodeForTest("", 1, 1), new NodeForTest(""
                , 1, 1), -10000, true, true));
    }
}
