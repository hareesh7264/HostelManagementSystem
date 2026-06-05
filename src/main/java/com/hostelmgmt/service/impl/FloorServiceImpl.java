package com.hostelmgmt.service.impl;

import com.hostelmgmt.dto.FloorDto;
import com.hostelmgmt.entity.Floor;
import com.hostelmgmt.exception.FloorIdNotFoundException;
import com.hostelmgmt.repository.FloorRepository;
import com.hostelmgmt.service.FloorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {
    private final FloorRepository floorRepository;

    public FloorServiceImpl(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override
    public Floor createFloor(FloorDto dto) {
        if (floorRepository.existsByFloorName(dto.getFloorName()))
            throw new RuntimeException("FloorName Already existed with name");
        if (floorRepository.existsByFloorNumber(dto.getFloorNumber()))
            throw new RuntimeException("Floor Number Already existed");

        Floor floor = new Floor();
        floor.setFloorName(dto.getFloorName());
        floor.setFloorNumber(dto.getFloorNumber());
        floor.setTotalRooms(dto.getTotalRooms());
        floor.setIsActive(true);
        return floorRepository.save(floor);
    }

    @Override
    public Floor updateFloor(Long floorId, FloorDto dto) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new FloorIdNotFoundException("Flood not found with id " + floorId));

        floor.setFloorName(dto.getFloorName());
        floor.setFloorNumber(dto.getFloorNumber());
        floor.setTotalRooms(dto.getTotalRooms());
        floor.setIsActive(true);
        return floorRepository.save(floor);
    }

    @Override
    public List<FloorDto> getFloor() {

        List<Floor> floor = floorRepository.findAll();

        return floor.stream()
                .map(this::dto)
                .toList();

    }

    @Override
    public Floor getFloorById(Long floorId) {
        return floorRepository.findById(floorId)
                .orElseThrow(() -> new FloorIdNotFoundException("Floor not found with Id " + floorId));
    }

    @Override
    public void deleteFloor(Long floorId) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new FloorIdNotFoundException("Floor not found with Id " + floorId));
        floorRepository.delete(floor);

    }

    public FloorDto dto(Floor floor) {
        FloorDto floorDto = new FloorDto();

        floorDto.setFloorId(floor.getFloorId());
        floorDto.setFloorName(floor.getFloorName());
        floorDto.setFloorNumber(floor.getFloorNumber());
        floorDto.setTotalRooms(floor.getTotalRooms());
        floorDto.setIsActive(floor.getIsActive());
        return floorDto;

    }
}
