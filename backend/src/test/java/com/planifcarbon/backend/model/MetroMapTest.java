package com.planifcarbon.backend.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MetroMap.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class MetroMapTest {

    static Stream<Arguments> generateDataNode() {
        return Stream.of(Arguments.of("A", 1.0, 2.0, Station.class), Arguments.of("Maison", 1.0, 2.0, PersonalizedNode.class));
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
        assertDoesNotThrow(() -> map.addSegmentMetro(new NodeForTest("A", 0, 0), new NodeForTest("B", 0, 0), 10.0, 40000, "1"));
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
//        assertEquals(nbSegments, map.getSegments(new NodeForTest(stationName, 0.0, 0.0)).size());
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
        System.out.println(station.getSchedules());
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

    //       AZH  Utils for debugging and observation of results of functions' calls

    /**
     * {@summary Print to explore all schedules.}
     */
   // @Test
   // public void simplePrintSchedules() {
   //     System.out.println("============================ Print schedules  =====================================");
   //     MetroMap map = new MetroMap();
   //     assertDoesNotThrow(map::initializeFields);
   //     int[] count1 = {0};
   //     Map<String, Station> stations = map.getStations();
   //     stations.forEach((key, value) -> {
   //         // System.out.println(value.toString());
   //         count1[0]++;
   //         value.getSchedules();
   //     });
   //     System.out.println("nb stations = " + count1[0]);
   //     System.out.println("============================ End print schedules  ===================================");
   // }

    /**
     * {@summary Print to explore the results of getDurations() method call.}
     */
   // @ParameterizedTest
   // @ValueSource(strings = {"Jussieu"})
   // public void simplePrintGetDurations(String name) {
   //     System.out.println("============= Print getDurations  ===========================================");
   //     MetroMap map = new MetroMap();
   //     assertDoesNotThrow(map::initializeFields);
   //     int[] count = {0};
   //     Map<Node, Set<Segment>> graph = map.getGraph();
   //     graph.forEach((key, value) -> {
   //         if (key.getName().equals(name)) {
   //             value.forEach((segm) -> {
   //                 System.out.println(segm.getEndPoint().getName() + " - " + segm.getDuration());
   //                 count[0]++;
   //             });
   //         }
   //     });
   //     System.out.println("nb stations connected with " + name + "= " + count[0]);
   //     System.out.println("============= End print getDurations  ===========================================");
   // }

    /**
     * {@summary Print schedules of Station (i.e. travel time from schKey to station)
     * and summ with schedule of line => it's timeTable in Station.}
     * Useful to check and getDurations in MetroMap and testGetSchedules() in StationTest.
     */
   // @ParameterizedTest
   // @ValueSource(strings = {"Duroc"})
   // public void simplePrintCummulativeSchedules(String name) {
   //     System.out.println("============= cummulative getSchedules results observation ================");
   //     MetroMap map = new MetroMap();
   //     assertDoesNotThrow(map::initializeFields);
   //     int[] count2 = {0};
   //     Station testingStation = map.getStationByName(name);
   //     Map<ScheduleKey, Integer> sh = testingStation.getSchedules();
   //     sh.forEach((scheduleKey, value) -> {
   //         System.out.println(scheduleKey.toString() + " : " + value);
   //         List<Integer> times = scheduleKey.getMetroLine().getSchedules();
   //         for (int i = 0; i < times.size(); i++) {
   //             System.out.println("departure time of " + scheduleKey.toString() + " = " + times.get(i));
   //         }
   //         count2[0]++;
   //     });
   //     System.out.println("nb schedules = " + count2[0] + "\n");
   // }

    /**
     * {@summary Print the result of getNearestDepartureTime() method call.}
     * Return only one nearest departure time.
     */
   //  @ParameterizedTest
   //  @CsvSource({"58500, Duroc"})
   //  public void simplePrintgetNearestDepartureTime(int time, String name) {
   //      System.out.println("\n================ Best opportunity observation ========================================");
   //      MetroMap map = new MetroMap();
   //      assertDoesNotThrow(map::initializeFields);
   //      Station testingStation = map.getStationByName(name);
   //      Map.Entry<MetroLine, Integer> best = map.getNearestDepartureTime(time, testingStation);
   //      System.out.println("From station " + testingStation.toString() + " departure time " + time);
   //      System.out.println("            the nearest train " + best.getKey() + " on time = " + best.getValue());
   //      System.out.println("================ End best opportunity observation =====================================\n");
   //  }

    /**
     * {@summary Print the path obtained after Dikjstra() method call.}
     *           WARNING : !!!!!!!!!!! print from FINISH to START !!!!!!!!!!!!!!
     */
    @ParameterizedTest
    @CsvSource({"58100, Duroc, Palais Royal - Musée du Louvre"})
    public void simplePrintPathDikjstra(int timeStart, String nameStart, String nameFinish) {
        System.out.println("\n\n================ Print path from Dikjstra  ===========================================");
        MetroMap map = new MetroMap();
        assertDoesNotThrow(map::initializeFields);
        Station testingStation = map.getStationByName(nameStart);

        Map<Node, Node> dijkstra = map.Dijkstra(testingStation, timeStart);

        Node arrive = map.getStationByName(nameFinish);
        Node current = arrive;
        Node end = testingStation;

        int c = 10;     // limit for potentual loops caused by imperfection of the algorithm/data

        while (!current.equals(end) && (c > 0)) {
            System.out.println("arr : " + current + " - dep : " + dijkstra.get(current));
            current = dijkstra.get(current);
            c--;
        }
        System.out.println("================ End Print path from Dikjstra =====================================\n\n");
    }

    /**
     * {@summary Print all paires (Child, Parent) obtained after Dikjstra() method call.}
     * Return only one nearest departure time.
     */
   // @ParameterizedTest
   // @CsvSource({"58100, Duroc"})
//
   // public void simplePrintgetChildsParents(int timeStart, String nameStart) {
   //     System.out.println("\n================ Print all parents and child (Dikjstra) ===============================");
   //     MetroMap map = new MetroMap();
   //     assertDoesNotThrow(map::initializeFields);
   //     Station testingStation = map.getStationByName(nameStart);
//
   //     Map<Node, Node> dijkstra = map.Dijkstra(testingStation, timeStart);
   //     dijkstra.forEach((ch1, par) -> {
   //         if (par != null) {
   //             System.out.println("child " + ch1.toString() + " : parent " + par.toString());
   //         }
   //     });
   //     System.out.println("================ End Print  all parents and child (Dikjstra) ===========================\n");
   // }
//

}
