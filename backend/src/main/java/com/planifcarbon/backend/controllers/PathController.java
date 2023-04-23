package com.planifcarbon.backend.controllers;

import com.planifcarbon.backend.dtos.DjikstraSearchResultDTO;
import com.planifcarbon.backend.dtos.DummyDTO;
import com.planifcarbon.backend.services.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller used to calculate the paths using time and/or distance.
 */
@RestController
@RequestMapping("/api/path")
public class PathController {
    private static final Logger logger = LoggerFactory.getLogger(PathController.class);
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    /**
     * Calculate the best path from start to end using provided method (time / distance).
     * @param start the starting position.
     * @param end the final destination.
     * @param method the method to use to calculate the best path.
     * @return The best path according the method.
     */
    @GetMapping("/best-path")
    public ResponseEntity<List<DjikstraSearchResultDTO>> getBestTimePath(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "time") Integer time,
            @RequestParam(name = "method") String method
    ) {
        logger.info("Request to get best path from {} to {} at {} by {}", start, end, time, method);
        return ResponseEntity.ok(this.pathService.getBestPath(start, end, time, method));
    }
}
