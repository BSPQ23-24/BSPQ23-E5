package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * REST controller for managing Nationality entities.
 */
@RestController
@RequestMapping("/nationalities")
public class NationalityRestController {
    private final NationalityService nationalityService;

    /**
     * Constructor for NationalityRestController.
     *
     * @param nationalityService The NationalityService instance to be used by the controller.
     */
    public NationalityRestController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    /**
     * Endpoint to get all nationalities.
     *
     * @return ResponseEntity containing a Set of Nationality objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Set<Nationality>> getAllNationalities() {
        return ResponseEntity.ok(nationalityService.getAllNationalities());
    }

}
