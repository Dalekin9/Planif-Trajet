package com.planifcarbon.backend.parser;

import com.planifcarbon.backend.dtos.NodeDTO;
import com.planifcarbon.backend.dtos.SegmentMetroDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the {@link Parser} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Parser.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
public class ParserTest extends Assertions {
    String map_data = "data/map_data.csv";
    String test_schedule = "data/timetables.csv";


    /**
     * Tests the splitString method in the Parser class using parameterized testing with CSV input.
     *
     * @param reg   a string representing the separator for splitting the input string
     * @param part1 the first part of the input string to be split
     * @param part2 the second part of the input string to be split
     */
    @ParameterizedTest
    @CsvSource({"';',abcdef,26443", "':',gyeuzgy$%,0ebuebz", "',',ijfioe098,3093:8", "' ',jpozejjfe,buzba(nzz)"})
    public void testSplit(String reg, String part1, String part2) {
        String line = part1 + reg + part2;
        String[] expected = new String[]{part1, part2};
        assertArrayEquals(expected, Parser.splitString(reg, line));
    }


    /**
     * Test methods for the conversion of string representation of time and duration to integers.
     *
     * @param duration duration string to parse.
     * @param expected duration integer in seconds expected.
     */
    @ParameterizedTest
    @CsvSource({"0:00, 0", "0:01, 1", "1:30, 13", "12:34, 124"})
    public void testDuration(String duration, int expected) {
        assertEquals(expected, Parser.durationStringToInt(duration));
    }

    /**
     * Tests the timeStringToInt method in the Parser class, which converts a time string
     * in the format "hh:mm" to an integer representing the number of seconds.
     *
     * @param time     the time string to be converted
     * @param expected the expected number of seconds after conversion
     */
    @ParameterizedTest
    @CsvSource({"00:00, 0", "00:01, 60", "01:30, 5400", "12:34, 45240"})
    public void testTime(String time, int expected) {
        assertEquals(expected, Parser.timeStringToInt(time));
    }

    /**
     * Tests the method parse() for a {@code NullPointerException} when the provided file names are not valid.
     *
     * @throws NullPointerException if the file names are not valid
     */
    @Test
    public void testParseException() {
        assertThrows(NullPointerException.class, () -> Parser.instance.parse("notAFile1", "notAFile2"));
    }

    /**
     * Tests the method calculateStationsAndSegments() for a {@code NullPointerException} when the provided file name
     * is not valid.
     *
     * @throws NullPointerException if the file name is not valid
     */
    @Test
    public void testCalculateStationsAndSegmentException() {
        assertThrows(NullPointerException.class, () -> Parser.calculateStationsAndSegments("notAFile"));
    }

    /**
     * Tests that the Parser correctly parses the map_data file and creates stations and segments.
     *
     * @throws FileNotFoundException if map_data file not found
     * @throws IOException           if error occurs while reading the file
     */
    @Test
    public void testCalculateStationsAndSegmentsList() throws FileNotFoundException, IOException {
        Parser.calculateStationsAndSegments(map_data);
        assertNotEquals(0, Parser.getStations().size());
        assertNotEquals(0, Parser.getSegmentMetro().size());
        List<NodeDTO> stationDTOs = new ArrayList<>();
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

    /**
     * Tests the {@code calculateSchedules()} method of the {@code Parser} class by verifying that a {@code
     * NullPointerException} is thrown when the specified file is not found, and by verifying that the method
     * correctly populates the {@code metroLineSchedules} map when given a valid input file.
     */
    @Test
    public void testCalculateSchedulesException() {
        assertThrows(NullPointerException.class, () -> Parser.calculateSchedules("notAFile"));
    }

    /**
     * Tests the {@code Parser} class's ability to read and parse a schedule file and generate a valid
     * {@code Map<Integer, List<Schedule>>>} containing schedules for each metro line.
     *
     * @throws FileNotFoundException if the test schedule file cannot be found
     * @throws IOException           if there is an error reading the test schedule file
     */
    @Test
    public void testGetSchedule() throws FileNotFoundException, IOException {
        Parser.calculateSchedules(test_schedule);
        assertNotEquals(0, Parser.getMetroLineSchedules().keySet().size());
    }
}
