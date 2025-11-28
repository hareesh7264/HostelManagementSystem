package com.hostelmgmt.controller;

import com.hostelmgmt.dto.*;
import com.hostelmgmt.service.RoomAllocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/allocations")
@RequiredArgsConstructor
public class RoomAllocationController {

    private final RoomAllocationService allocationService;

    @PostMapping ("/allocate")
    public ResponseEntity<AllocationResponse> allocate(@Valid @RequestBody AllocateRequest req) {
        return ResponseEntity.ok(allocationService.allocateRoom(req));
    }

    @PostMapping ("/moveOut")
    public ResponseEntity<AllocationResponse> moveOut(@Valid @RequestBody MoveOutRequest req) {
        return ResponseEntity.ok(allocationService.moveOut(req));
    }

    @PostMapping ("/switch")
    public ResponseEntity<AllocationResponse> switchBed(@Valid @RequestBody SwitchBedRequest req) {
        return ResponseEntity.ok(allocationService.switchBed(req));
    }

    @GetMapping ("/room/{roomId}")
    public ResponseEntity<List<AllocationResponse>> byRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(allocationService.getAllocationsByRoom(roomId));
    }

    @GetMapping ("/user/{userId}")
    public ResponseEntity<List<AllocationResponse>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(allocationService.getAllocationsByUser(userId));
    }
}
