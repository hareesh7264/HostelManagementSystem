package com.hostelmgmt.service;

import com.hostelmgmt.dto.FloorDto;
import com.hostelmgmt.entity.Floor;

import java.util.List;

public interface FloorService {
    Floor createFloor(FloorDto dto);
    Floor updateFloor(Long floorId, FloorDto dto);
    List<Floor> getFloor();
    Floor getFloorById(Long floorId);
    void deleteFloor(Long floorId);

}
