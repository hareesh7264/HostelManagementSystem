package com.hostelmgmt.service;

import com.hostelmgmt.dto.*;

import java.util.List;

public interface RoomAllocationService {
    AllocationResponse allocateRoom(AllocateRequest req);
    AllocationResponse moveOut(MoveOutRequest req);
    AllocationResponse switchBed(SwitchBedRequest req);
    List<AllocationResponse> getAllocationsByRoom(Long roomId);
    List<AllocationResponse> getAllocationsByUser(Long userId);
}
