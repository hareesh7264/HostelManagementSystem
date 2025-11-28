package com.hostelmgmt.service.impl;

import com.hostelmgmt.dto.*;
import com.hostelmgmt.entity.*;
import com.hostelmgmt.enums.AllocationStatus;
import com.hostelmgmt.exception.*;
import com.hostelmgmt.repository.*;
import com.hostelmgmt.service.RoomAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomAllocationServiceImpl implements RoomAllocationService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final RoomAllocationRepository allocationRepository;

    @Override
    public AllocationResponse allocateRoom(AllocateRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Room room = roomRepository.findById(req.getRoomId())
                .orElseThrow(() -> new RoomIdNotFoundException("Room not found"));

        Bed bed = bedRepository.findById(req.getBedId())
                .orElseThrow(() -> new BedNotFoundException("Bed not found"));

        // user active
        if (user.getActive() == Boolean.FALSE) throw new BadRequestException("User is not active");

        // room active
        if (room.getIsActive() == Boolean.FALSE) throw new BadRequestException("Room is not active");

        // bed belonging
        if (!bed.getRoom().getRoomId().equals(room.getRoomId()))
            throw new BadRequestException("Selected bed does not belong to requested room");

        // bed occupancy
        if (Boolean.TRUE.equals(bed.getIsOccupied()))
            throw new BadRequestException("Selected bed is already occupied");

        // user already allocated?
        allocationRepository.findByUserUserIdAndStatus(user.getUserId(), AllocationStatus.ALLOCATED)
                .ifPresent(a -> { throw new BadRequestException("User already has an active allocation"); });

        // bed already allocated (safety)
        allocationRepository.findByBedBedIdAndStatus(bed.getBedId(), AllocationStatus.ALLOCATED)
                .ifPresent(a -> { throw new BadRequestException("Bed already has an active allocation"); });

        // perform allocation
        bed.setAssignedUser(user);
        bed.setIsOccupied(true);
        bedRepository.save(bed);

        RoomAllocation alloc = RoomAllocation.builder()
                .user(user)
                .room(room)
                .roomNumber(room.getRoomNumber())
                .bed(bed)
                .bedNumber(bed.getBedNumber())
                .moveInDate(LocalDate.now())
                .status(AllocationStatus.ALLOCATED)
                .build();
        alloc = allocationRepository.save(alloc);

        return toResponse(alloc);
    }

    @Override
    public AllocationResponse moveOut(MoveOutRequest req) {
        RoomAllocation alloc = allocationRepository.findById(req.getAllocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Allocation not found"));

        if (alloc.getStatus() != AllocationStatus.ALLOCATED)
            throw new BadRequestException("Allocation is not active");

        // free bed
        Bed bed = alloc.getBed();
        bed.setAssignedUser(null);
        bed.setIsOccupied(false);
        bedRepository.save(bed);

        alloc.setStatus(AllocationStatus.MOVED_OUT);
        alloc.setMoveOutDate(LocalDate.now());
        allocationRepository.save(alloc);

        return toResponse(alloc);
    }

    @Override
    public AllocationResponse switchBed(SwitchBedRequest req) {
        // find current active allocation for user
        RoomAllocation current = allocationRepository.findByUserUserIdAndStatus(req.getUserId(), AllocationStatus.ALLOCATED)
                .orElseThrow(() -> new ResourceNotFoundException("User has no active allocation"));

        Bed newBed = bedRepository.findById(req.getNewBedId())
                .orElseThrow(() -> new ResourceNotFoundException("Requested new bed not found"));

        if (Boolean.TRUE.equals(newBed.getIsOccupied()))
            throw new BadRequestException("Requested new bed is already occupied");

        // free old bed
        Bed old = current.getBed();
        old.setAssignedUser(null);
        old.setIsOccupied(false);
        bedRepository.save(old);

        // mark current allocation as moved out
        current.setStatus(AllocationStatus.MOVED_OUT);
        current.setMoveOutDate(LocalDate.now());
        allocationRepository.save(current);

        // assign new bed
        newBed.setAssignedUser(current.getUser());
        newBed.setIsOccupied(true);
        bedRepository.save(newBed);

        RoomAllocation newAlloc = RoomAllocation.builder()
                .user(current.getUser())
                .bed(newBed)
                .room(newBed.getRoom())
                .moveInDate(LocalDate.now())
                .status(AllocationStatus.ALLOCATED)
                .build();
        newAlloc = allocationRepository.save(newAlloc);

        return toResponse(newAlloc);
    }

    @Override
    public List<AllocationResponse> getAllocationsByRoom(Long roomId) {
        return allocationRepository.findByRoomRoomId(roomId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AllocationResponse> getAllocationsByUser(Long userId) {
        return allocationRepository.findByUserUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    private AllocationResponse toResponse(RoomAllocation alloc) {
        return AllocationResponse.builder()
                .allocationId(alloc.getId())
                .userId(alloc.getUser().getUserId())
                .roomId(alloc.getRoom().getRoomId())
                .roomNumber(alloc.getRoom().getRoomNumber())
                .bedId(alloc.getBed().getBedId())
                .bedNumber(alloc.getBed().getBedNumber())
                .moveInDate(alloc.getMoveInDate())
                .moveOutDate(alloc.getMoveOutDate())
                .status(alloc.getStatus())
                .build();
    }
}
