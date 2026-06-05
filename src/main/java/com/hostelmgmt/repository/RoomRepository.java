package com.hostelmgmt.repository;

import com.hostelmgmt.entity.Floor;
import com.hostelmgmt.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(String roomNumber);
    List<Room> findByFloorNumber(Integer floorNumber);

    int countByFloor(Floor floor);

    int countByFloorFloorId(Long floorId);
    List<Room> findByFloorFloorId(Long floorId);
}

