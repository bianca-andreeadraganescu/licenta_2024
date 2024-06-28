package com.madmin.policies.controller;

import com.madmin.policies.object.WorkStation;
import com.madmin.policies.services.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workstations")
public class WorkStationController {
    @Autowired
    private WorkStationService service;

    @PostMapping
    public ResponseEntity<WorkStation> createWorkStation(@RequestBody WorkStation workStation) {
        WorkStation savedWorkStation = service.saveWorkStation(workStation);
        return ResponseEntity.ok(savedWorkStation);
    }

    @GetMapping
    public ResponseEntity<List<WorkStation>> getAllWorkStations() {
        List<WorkStation> workStations = service.getAllWorkStations();
        return ResponseEntity.ok(workStations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkStation(@PathVariable Long id) {
        service.deleteWorkStation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkStation> getWorkStationById(@PathVariable Long id) {
        WorkStation workStation = service.getWorkStationById(id);
        if (workStation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(workStation);
    }
}
