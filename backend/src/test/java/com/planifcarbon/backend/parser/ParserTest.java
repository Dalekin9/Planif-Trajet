package com.planifcarbon.backend.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.FileNotFoundException;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParserTest extends Assertions {
    String map_data = System.getProperty("user.dir")+"/src/test/java/com/planifcarbon/backend/parser/map_data.csv";
    String test_schedule = System.getProperty("user.dir")+"/src/test/java/com/planifcarbon/backend/parser/test_schedule.csv";

    @ParameterizedTest
    @CsvSource({"';',abcdef,26443", "':',gyeuzgy$%,0ebuebz", "',',ijfioe098,3093:8", "' ',jpozejjfe,buzba(nzz)"})
    public void testSplit(String reg, String part1, String part2){
        String line = part1+reg+part2;
        String[] expected = new String[] {part1,part2};
        assertArrayEquals(expected, Parser.splitString(reg, line));
    }

    @ParameterizedTest
    @CsvSource({"0:00, 0","0:01, 1","1:30, 90","12:34, 754"})
    public void testDuration(String duration, int expected){
        assertEquals(expected, Parser.durationStringToInt(duration));
    }

    @ParameterizedTest
    @CsvSource({"00:00, 0","00:01, 60","01:30, 5400","12:34, 45240"})
    public void testTime(String time, int expected){
        assertEquals(expected, Parser.timeStringToInt(time));
    }

    @ParameterizedTest
    @CsvSource({"0, 0","1, 1000","2.5, 2500","42.195, 42195"})
    public void testDistance(String distance, int expected){
        assertEquals(expected, Parser.distanceStringToInt(distance));
    }

    @Test
    public void testGetNodeException(){
        assertThrows(FileNotFoundException.class, () -> Parser.getNodeList("notAFile"));
    }

    @Test
    public void testGetNodeList() throws FileNotFoundException {
        Collection<Collection<Object>> nodes = Parser.getNodeList(map_data);
        assertNotNull(nodes);
        assertEquals(3540, nodes.size());

        Collection<Object> node1 = nodes.iterator().next();
        System.out.println("node1 : "+node1);
        assertEquals(node1.size(), 3);
        assertTrue(node1.contains("Lourmel"));
        assertTrue(node1.contains(2.282242f));
        assertTrue(node1.contains(48.83866f));

        Collection<Object> lastNode = null;
        for (Collection<Object> node : nodes) {
            lastNode = node;
        }
        assert lastNode != null;
        System.out.println("lastNode : "+lastNode);
        assertEquals(lastNode.size(), 3);
        assertTrue(lastNode.contains("Louis Blanc"));
        assertTrue(lastNode.contains(2.364365f));
        assertTrue(lastNode.contains(48.881184f));
    }

    @Test
    public void testGetSegmentException(){
        assertThrows(FileNotFoundException.class, () -> Parser.getSegmentList("notAFile"));
    }

    @Test
    public void testGetSegmentList() throws FileNotFoundException {
        Collection<Collection<Object>> segments = Parser.getSegmentList(map_data);
        assertNotNull(segments);
        assertEquals(1770, segments.size());

        Collection<Object> segment1 = segments.iterator().next();
        System.out.println("segment1 : "+segment1);
        assertEquals(segment1.size(), 5);
        assertTrue(segment1.contains("Lourmel"));
        assertTrue(segment1.contains("Boucicaut"));
        assertTrue(segment1.contains("8 variant 1"));

        Collection<Object> lastSegment = null;
        for (Collection<Object> segment : segments) {
            lastSegment = segment;
        }
        assert lastSegment != null;
        System.out.println("lastSegment : "+lastSegment);
        assertEquals(lastSegment.size(), 5);
        assertTrue(lastSegment.contains("Jaurès"));
        assertTrue(lastSegment.contains("Louis Blanc"));
        assertTrue(lastSegment.contains("7B variant 2"));
    }

    @Test
    public void testGetMetroException(){
        assertThrows(FileNotFoundException.class, () -> Parser.getMetroStations("notAFile"));
    }

    @Test
    public void testGetMetroStations() throws FileNotFoundException {
        Collection<Collection<Object>> stations = Parser.getMetroStations(map_data);
        assertNotNull(stations);
        assertEquals(1770, stations.size());

        Collection<Object> station1 = stations.iterator().next();
        System.out.println("station1 : "+station1);
        assertEquals(station1.size(), 9);
        assertTrue(station1.contains("Lourmel"));
        assertTrue(station1.contains(2.282242f));
        assertTrue(station1.contains(48.83866f));
        assertTrue(station1.contains("Boucicaut"));
        assertTrue(station1.contains(2.2879183f));
        assertTrue(station1.contains(48.841022f));
        assertTrue(station1.contains("8 variant 1"));

        Collection<Object> lastStation = null;
        for (Collection<Object> station : stations) {
            lastStation = station;
        }
        assert lastStation != null;
        System.out.println("lastStation : "+lastStation);
        assertEquals(lastStation.size(), 9);
        assertTrue(lastStation.contains("Jaurès"));
        assertTrue(lastStation.contains(2.3704655f));
        assertTrue(lastStation.contains(48.882328f));
        assertTrue(lastStation.contains("Louis Blanc"));
        assertTrue(lastStation.contains(2.364365f));
        assertTrue(lastStation.contains(48.881184f));
        assertTrue(lastStation.contains("7B variant 2"));
    }

    @Test
    public void testGetScheduleException(){
        assertThrows(FileNotFoundException.class, () -> Parser.getSchedule("notAFile"));
    }

    @Test
    public void testGetSchedule() throws FileNotFoundException {
        Map<String, Map<String, Collection<Integer>>> schedule = Parser.getSchedule(test_schedule);

        assertNotNull(schedule);

        assertTrue(schedule.containsKey("1"));
        assertTrue(schedule.containsKey("2"));
        assertTrue(schedule.containsKey("3"));
        assertTrue(schedule.containsKey("4"));
        assertTrue(schedule.containsKey("5"));
        assertTrue(schedule.containsKey("6"));

        assertTrue(schedule.get("1").containsKey("La Défense"));
        assertTrue(schedule.get("2").containsKey("Nation"));
        assertTrue(schedule.get("3").containsKey("Galieni"));
        assertTrue(schedule.get("4").containsKey("Bagneux"));
        assertTrue(schedule.get("5").containsKey("Place d'Italie"));
        assertTrue(schedule.get("6").containsKey("Nation"));
    }

}
