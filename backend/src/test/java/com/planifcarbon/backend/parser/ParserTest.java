package com.planifcarbon.backend.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import com.planifcarbon.backend.dtos.StationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Parser.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class ParserTest extends Assertions {
    String map_data = "src/test/resources/map_data.csv";
    String test_schedule = "src/test/resources/timetables.csv";

    @ParameterizedTest
    @CsvSource({"';',abcdef,26443", "':',gyeuzgy$%,0ebuebz", "',',ijfioe098,3093:8", "' ',jpozejjfe,buzba(nzz)"})
    public void testSplit(String reg, String part1, String part2) {
        String line = part1 + reg + part2;
        String[] expected = new String[] {part1, part2};
        assertArrayEquals(expected, Parser.splitString(reg, line));
    }

    @ParameterizedTest
    @CsvSource({"0:00, 0", "0:01, 1", "1:30, 90", "12:34, 754"})
    public void testDuration(String duration, int expected) { assertEquals(expected, Parser.durationStringToInt(duration)); }

    @ParameterizedTest
    @CsvSource({"00:00, 0", "00:01, 60", "01:30, 5400", "12:34, 45240"})
    public void testTime(String time, int expected) { assertEquals(expected, Parser.timeStringToInt(time)); }

    @Test
    public void testParseException() { assertThrows(FileNotFoundException.class, () -> Parser.parse("notAFile1", "notAFile2")); }

    @Test
    public void testCalculateStationsAndSegmentException() {
        assertThrows(FileNotFoundException.class, () -> Parser.calculateStationsAndSegments("notAFile"));
    }

    @Test
    public void testCalculateStationsAndSegmentsList() throws FileNotFoundException, IOException {
        Parser.calculateStationsAndSegments(map_data);
        assertNotEquals(0, Parser.getStations().size());
        assertNotEquals(0, Parser.getSegmentMetro().size());
        List<StationDTO> stationDTOs = new ArrayList<>();
        Parser.getStations().forEach(station -> {
            assertFalse(stationDTOs.contains(station));
            stationDTOs.add(station);
        });

        List<SegmentMetroDTO> segmentMetroDTOs = new ArrayList<>();
        Parser.getSegmentMetro().forEach(segment -> {
            assertFalse(segmentMetroDTOs.contains(segment));
            segmentMetroDTOs.add(segment);
        });

        assertEquals(93, Parser.getMetroLines().keySet().size());
    }

    @Test
    public void testCalculateSchedulesException() {
        assertThrows(FileNotFoundException.class, () -> Parser.calculateSchedules("notAFile"));
    }

    @Test
    public void testGetSchedule() throws FileNotFoundException, IOException {
        Parser.calculateSchedules(test_schedule);
        assertNotEquals(0, Parser.getMetroLineSchedules().keySet().size());
    }
}
