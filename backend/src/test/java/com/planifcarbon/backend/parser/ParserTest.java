package com.planifcarbon.backend.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParserTest extends Assertions {

    @ParameterizedTest
    @CsvSource({"';',abcdef,26443", "':',gyeuzgy$%,0ebuebz", "',',ijfioe098,3093:8", "' ',jpozejjfe,buzba(nzz)"})
    public void testSplit(String reg, String part1, String part2){
        String line = part1+reg+part2;
        String[] expected = new String[] {part1,part2};
        assertArrayEquals(expected, Parser.splitString(reg, line));
    }

    @ParameterizedTest
    @CsvSource({"0:00, 0","0:01, 1000","1:30, 90000","12:34, 754000"})
    public void testDuration(String duration, int expected){
        assertEquals(expected, Parser.durationStringToInt(duration));
    }

    @ParameterizedTest
    @CsvSource({"00:00, 0","00:01, 60000","01:30, 5400000","12:34, 45240000"})
    public void testTime(String time, int expected){
        assertEquals(expected, Parser.timeStringToInt(time));
    }

    @ParameterizedTest
    @CsvSource({"0, 0","1, 1000","2.5, 2500","42.195, 42195"})
    public void testDistance(String distance, int expected){
        assertEquals(expected, Parser.distanceStringToInt(distance));
    }

}
