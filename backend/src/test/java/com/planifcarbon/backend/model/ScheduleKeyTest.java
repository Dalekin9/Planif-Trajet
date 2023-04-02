package com.planifcarbon.backend.model;

import com.planifcarbon.backend.parser.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Parser.class)
@TestPropertySource(
        locations = "classpath:application-tests.properties")
class ScheduleKeyTest {
    @ParameterizedTest
    @CsvSource({"nameStation, nameMetro"})
    public void testScheduleKeyCreation(String nameStation, String nameMetro) {
        Station station = new Station(nameStation, 0, 0);
        Set<Station> set = new HashSet<>();
        set.add(station);
        MetroLine metroLine = new MetroLine(nameMetro, set, Collections.emptyList());
        ScheduleKey key =  new ScheduleKey(station, metroLine);
        assertNotNull(key);
        assertNotNull(key.getMetroLine());
        assertNotNull(key.getTerminusStation());
    }

    @ParameterizedTest
    @CsvSource({"nameStation, nameMetro, nameStation, nameMetro"})
    public void testScheduleKeyEquity(String n1, String m1, String n2, String m2) {
        Station station1 = new Station(n1, 0, 0);
        Station station2 = new Station(n2, 0, 0);
        Set<Station> set1 = new HashSet<>();
        set1.add(station1);
        MetroLine metroLine1 = new MetroLine(m1, set1, Collections.emptyList());
        Set<Station> set2 = new HashSet<>();
        set2.add(station2);
        MetroLine metroLine2 = new MetroLine(m2, set2, Collections.emptyList());
        ScheduleKey key1 =  new ScheduleKey(station1, metroLine1);
        ScheduleKey key2 =  new ScheduleKey(station2, metroLine2);
        assertEquals(key1, key2);
    }

    @ParameterizedTest
    @CsvSource({"nameStation, nameMetro1, nameStation, nameMetro2"})
    public void testScheduleKeyNotEquity(String n1, String m1, String n2, String m2) {
        Station station1 = new Station(n1, 0, 0);
        Station station2 = new Station(n2, 0, 0);
        Set<Station> set1 = new HashSet<>();
        set1.add(station1);
        MetroLine metroLine1 = new MetroLine(m1, set1, Collections.emptyList());
        Set<Station> set2 = new HashSet<>();
        set2.add(station2);
        MetroLine metroLine2 = new MetroLine(m2, set2, Collections.emptyList());
        ScheduleKey key1 =  new ScheduleKey(station1, metroLine1);
        ScheduleKey key2 =  new ScheduleKey(station2, metroLine2);
        assertNotEquals(key1, key2);
    }
}
