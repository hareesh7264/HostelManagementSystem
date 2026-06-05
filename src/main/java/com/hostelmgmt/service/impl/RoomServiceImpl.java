package com.hostelmgmt.service.impl;

import com.hostelmgmt.dto.RoomDto;
import com.hostelmgmt.entity.*;
import com.hostelmgmt.exception.*;
import com.hostelmgmt.repository.*;
import com.hostelmgmt.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    private final FloorRepository floorRepository;
    private final BedRepository bedRepository;

    public RoomServiceImpl(RoomRepository roomRepository, FloorRepository floorRepository, BedRepository bedRepository) {
        this.roomRepository = roomRepository;
        this.floorRepository = floorRepository;
        this.bedRepository = bedRepository;
    }

    @Override
    public RoomDto createRoom(RoomDto dto) {

        // Room number must be unique
        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())) {
            throw new RoomAlreadyExistsException("Room number already exists: " + dto.getRoomNumber());
        }

        // Check if floor exists
        Floor floor = floorRepository.findByFloorNumber(dto.getFloorNumber())
                .orElseThrow(() -> new FloorNotFoundException("Floor number not found: " + dto.getFloorNumber()));

        // Count existing rooms for this floor
        int existingRooms = roomRepository.countByFloor(floor);

        // Check if already full
        if (existingRooms >= floor.getTotalRooms()) {
            throw new RoomsAlreadyCreatedException("All rooms are already created for this floor");
        }

//        Room room = Room.builder()
//                .roomNumber(dto.getRoomNumber())
//                .roomType(dto.getRoomType())
//                .roomCategory(dto.getRoomCategory())
//                .pricePerMonth(dto.getPricePerMonth())
//                .totalBeds(dto.getTotalBeds())
//                .floor(floor)
//                .floorNumber(floor.getFloorNumber())
//                .createdAt(LocalDateTime.now())
//                .isActive(true)
//                .build();

        // Convert DTO → Entity
        Room room = mapToEntity(dto, floor);
        Room savedRoom = roomRepository.save(room);

        // Auto-create beds
        List<Bed> beds = new ArrayList<>();
        for (int i = 1; i <= dto.getTotalBeds(); i++) {
            beds.add(Bed.builder()
                    .bedNumber("Bed-" + i)
                    .room(savedRoom)
                    .roomNumber(savedRoom.getRoomNumber())
                    .isOccupied(false)
                    .build());
        }
        bedRepository.saveAll(beds);
        savedRoom.setBeds(beds);


        return mapToDto(savedRoom);
    }

    @Override
    public RoomDto updateRoom(Long roomId, RoomDto dto) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomIdNotFoundException("Room not found"));

        // UNIQUE ROOM NUMBER VALIDATION
        if (roomRepository.existsByRoomNumber(dto.getRoomNumber())
                && !room.getRoomNumber().equals(dto.getRoomNumber())) {
            throw new RoomAlreadyExistsException(
                    "Room number already exists: " + dto.getRoomNumber());
        }

        Floor floor = floorRepository.findByFloorNumber(dto.getFloorNumber())
                .orElseThrow(() -> new FloorNotFoundException("Floor not found"));

        int oldBedCount = room.getTotalBeds();
        int newBedCount = dto.getTotalBeds();

        // Update fields
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setRoomCategory(dto.getRoomCategory());
        room.setPricePerMonth(dto.getPricePerMonth());
        room.setTotalBeds(newBedCount);
        room.setFloor(floor);
        room.setFloorNumber(floor.getFloorNumber());
        room.setIsActive(dto.getIsActive());
        room.setUpdatedAt(LocalDateTime.now());

        Room updatedRoom = roomRepository.save(room);

        // ---------- BED MANAGEMENT ----------
        List<Bed> existingBeds = bedRepository.findByRoom(updatedRoom);

        // CASE 1: Increase beds → Add new beds
        if (newBedCount > oldBedCount) {
            List<Bed> bedsToAdd = new ArrayList<>();
            for (int i = oldBedCount + 1; i <= newBedCount; i++) {
                bedsToAdd.add(Bed.builder()
                        .bedNumber("Bed-" + i)
                        .room(updatedRoom)
                            .roomNumber(updatedRoom.getRoomNumber())
                        .isOccupied(false)
                        .build());
            }
            bedRepository.saveAll(bedsToAdd);
        }

        // CASE 2: Decrease beds → Remove extra beds
        else if (newBedCount < oldBedCount) {

            List<Bed> bedsToRemove = existingBeds.stream()
                    .filter(bed -> extractNumber(bed.getBedNumber()) > newBedCount)
                    .toList();

            // don't delete occupied beds
            boolean occupiedExists = bedsToRemove.stream().anyMatch(Bed::getIsOccupied);

            if (occupiedExists) {
                throw new IllegalStateException("Cannot reduce beds. Some beds are occupied.");
            }

            bedRepository.deleteAll(bedsToRemove);
        }

        // CASE 3: Same bed count → Do nothing

        // Re-index all beds to maintain sequence Bed-1, Bed-2,..
        renumberBeds(updatedRoom);

        return mapToDto(updatedRoom);
    }

    private void renumberBeds(Room room) {
        List<Bed> allBeds = bedRepository.findByRoom(room);

        // sort by current bed number
        allBeds.sort(Comparator.comparingInt(b -> extractNumber(b.getBedNumber())));

        int counter = 1;

        for (Bed bed : allBeds) {
            bed.setBedNumber("Bed-" + counter);
            counter++;
        }

        bedRepository.saveAll(allBeds);
    }

    private int extractNumber(String bedNumber) {
        return Integer.parseInt(bedNumber.replace("Bed-", ""));
    }


    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        roomRepository.delete(room);
    }

    @Override
    public RoomDto getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        return mapToDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<RoomDto> getRoomsByFloor(int floorNumber) {
        Floor floor = floorRepository.findByFloorNumber(floorNumber)
                .orElseThrow(()-> new FloorNumberNotFoundException("Floor Number Not Found"));
        return roomRepository.findByFloorNumber(floorNumber)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private RoomDto mapToDto(Room room) {

        List<String> beds = room.getBeds() != null
                ? room.getBeds().stream().map(Bed::getBedNumber).toList()
                : Collections.emptyList();

        return RoomDto.builder()
                .roomId(room.getRoomId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .roomCategory(room.getRoomCategory())
                .pricePerMonth(room.getPricePerMonth())
                .totalBeds(room.getTotalBeds())
                .floorNumber(room.getFloorNumber())
                .isActive(room.getIsActive())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .beds(beds)
                .build();
    }

    private Room mapToEntity(RoomDto dto, Floor floor) {
        return Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(dto.getRoomType())
                .roomCategory(dto.getRoomCategory())
                .pricePerMonth(dto.getPricePerMonth())
                .totalBeds(dto.getTotalBeds())
                .floor(floor)
                .floorNumber(floor.getFloorNumber())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();
    }

}