package com.RouteBus.server.controller;

import com.RouteBus.server.model.Nationality;
import com.RouteBus.server.service.NationalityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nationalities")
public class NationalityRestController {
    private final NationalityService nationalityService;

    public NationalityRestController(NationalityService nationalityService) {
        this.nationalityService = nationalityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Nationality>> getAllNationalities() {
        List<Nationality> nationalities = nationalityService.getAllNationalities();
        return ResponseEntity.ok(nationalities);
    }

}
