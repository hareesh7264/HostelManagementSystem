package com.hostelmgmt.controller;

import com.hostelmgmt.dto.FloorDto;
import com.hostelmgmt.service.FloorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/floors")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping ("/create")
    public ResponseEntity<?> createFloor(@RequestBody FloorDto req) {
        return ResponseEntity.ok(floorService.createFloor(req));
    }

    @PutMapping ("/update/{floorId}")
    public ResponseEntity<?> updateFloor(@PathVariable Long floorId, @RequestBody FloorDto dto) {
        return ResponseEntity.ok(floorService.updateFloor(floorId, dto));
    }

    @GetMapping ("/all")
    public ResponseEntity<?> getFloors() {
        return ResponseEntity.ok(floorService.getFloor());
    }

    @GetMapping ("/floorId")
    public ResponseEntity<?> getFloorById(@PathVariable Long floorId) {
        return ResponseEntity.ok(floorService.getFloorById(floorId));
    }

    @DeleteMapping ("/{floorId}")
    public ResponseEntity<String> deleteFloor(@PathVariable Long floorId) {
        floorService.deleteFloor(floorId);
        return ResponseEntity.ok("Floor deleted Successfully.");
    }

}

