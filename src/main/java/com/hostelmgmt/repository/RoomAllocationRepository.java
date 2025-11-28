package com.hostelmgmt.repository;

import com.hostelmgmt.entity.RoomAllocation;
import com.hostelmgmt.enums.AllocationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomAllocationRepository extends JpaRepository<RoomAllocation, Long> {
    Optional<RoomAllocation> findByUserUserIdAndStatus(Long userId, AllocationStatus status);
    List<RoomAllocation> findByRoomRoomId(Long roomId);
    List<RoomAllocation> findByUserUserId(Long userId);

    // active allocation by bed
    Optional<RoomAllocation> findByBedBedIdAndStatus(Long bedId, AllocationStatus status);
}
