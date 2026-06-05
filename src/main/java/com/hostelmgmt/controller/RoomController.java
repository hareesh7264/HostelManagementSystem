package com.hostelmgmt.controller;

import com.hostelmgmt.dto.RoomDto;
import com.hostelmgmt.entity.Room;
import com.hostelmgmt.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/rooms")
public class RoomController {

    @Autowired
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.createRoom(dto));
    }

    @PutMapping ("/update/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RoomDto dto) {
        return ResponseEntity.ok(roomService.updateRoom(id, dto));
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok("Room deleted");
    }

    @GetMapping ("/{id}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping ("/floor/{floor}")
    public ResponseEntity<List<RoomDto>> getRoomsByFloor(@PathVariable int floor) {
        return ResponseEntity.ok(roomService.getRoomsByFloor(floor));
    }

}

