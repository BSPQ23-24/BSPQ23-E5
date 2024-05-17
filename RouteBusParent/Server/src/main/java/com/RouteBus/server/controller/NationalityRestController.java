package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/nationalities")
public class NationalityRestController {
    private final NationalityService nationalityService;

    public NationalityRestController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<Nationality>> getAllNationalities() {
        return ResponseEntity.ok(nationalityService.getAllNationalities());
    }

}
