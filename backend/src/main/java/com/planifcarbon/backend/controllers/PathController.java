package com.planifcarbon.backend.controllers;

import com.planifcarbon.backend.dtos.DummyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/path")
public class PathController {
    private static final Logger logger = LoggerFactory.getLogger(PathController.class);

    @GetMapping("/best-time-path")
    public ResponseEntity<DummyDTO> getBestTimePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best time path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/best-distance-path")
    public ResponseEntity<DummyDTO> getBestDistancePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best distance path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/best-time-distance-path")
    public ResponseEntity<DummyDTO> getBestTimeAndDistancePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end
    ) {
        logger.info("Request to get best time and distance path from {} to {}", start, end);
        return ResponseEntity.ok(null);
    }
}
