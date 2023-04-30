package com.planifcarbon.backend.controllers;

import java.util.List;

import com.planifcarbon.backend.dtos.NodeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.planifcarbon.backend.dtos.MetroDTO;
import com.planifcarbon.backend.dtos.StationCorrespondence;
import com.planifcarbon.backend.services.MetroService;


/**
 * The controller used to get information about the metro network.
 */
@RestController
@RequestMapping("/api/metro")
public class MetroController {

    private static final Logger logger = LoggerFactory.getLogger(MetroController.class);
    private final MetroService metroService;

    /**
     * Constructs a new MetroController object.
     *
     * @param metroService The MetroService object that the controller will use for processing Metro-related requests.
     */
    public MetroController(MetroService metroService) { this.metroService = metroService; }

    /**
     * Find the available metro lines in the network.
     * 
     * @return a list of metro lines
     */
    @GetMapping("/list")
    public ResponseEntity<List<MetroDTO>> getMetroList() {
        logger.info("Request to get Metro list");
        return ResponseEntity.ok(metroService.getMetros());
    }

    /**
     * Get the metro information using the line number.
     * 
     * @param metroId the metro line number to search with.
     * @return the information about the metro line.
     */
    @GetMapping("/{metroId}")
    public ResponseEntity<MetroDTO> getMetroInformation(@PathVariable String metroId) {
        logger.info("Request to get metro information by id : {}", metroId);
        return ResponseEntity.ok(metroService.getMetroByName(metroId));
    }

    /**
     * Get the stations with the higher number of metro lines
     * 
     * @return the list of best stations.
     */
    @GetMapping("/best-stations")
    public ResponseEntity<List<StationCorrespondence>> getBestStations() {
        logger.info("Request to get best stations in the network ");
        return ResponseEntity.ok(metroService.getBestStations());
    }

    /**
     * Get all stations correspondences
     * 
     * @return the list of stations with correspondences.
     */
    @GetMapping("/stations-correspondence")
    public ResponseEntity<List<StationCorrespondence>> getAllStationsCorrespondences() {
        logger.info("Request to get best stations in the network ");
        return ResponseEntity.ok(metroService.getAllStationsCorrespondence());
    }

    /**
     * Get all stations from netword
     *
     * @return the list of stations
     */
    @GetMapping("/stations")
    public ResponseEntity<List<NodeDTO>> getAllStations() {
        logger.info("Request to get all stations in the network ");
        return ResponseEntity.ok(metroService.getAllStations());
    }
}
