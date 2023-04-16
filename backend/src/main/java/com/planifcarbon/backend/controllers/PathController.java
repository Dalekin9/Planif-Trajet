package com.planifcarbon.backend.controllers;

import com.planifcarbon.backend.dtos.DummyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller used to calculate the paths using time and/or distance.
 */
@RestController
@RequestMapping("/api/path")
public class PathController {
    private static final Logger logger = LoggerFactory.getLogger(PathController.class);


    /**
     * Calculate the best path from start to end using time.
     * @param start the starting position.
     * @param end the final destination.
     * @return The best path using time.
     */
    @GetMapping("/best-time-path")
    public ResponseEntity<DummyDTO> getBestTimePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best time path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }

    /**
     * Calculate the best path from start to end using distance.
     * @param start the starting position.
     * @param end the final destination.
     * @return The best path using distance.
     */
    @GetMapping("/best-distance-path")
    public ResponseEntity<DummyDTO> getBestDistancePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best distance path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }

    /**
     * Calculate the best path from start to end using time and distance.
     * @param start the starting position.
     * @param end the final destination.
     * @return The best path using time and distance.
     */
    @GetMapping("/best-time-distance-path")
    public ResponseEntity<DummyDTO> getBestTimeAndDistancePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best time and distance path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }
}
