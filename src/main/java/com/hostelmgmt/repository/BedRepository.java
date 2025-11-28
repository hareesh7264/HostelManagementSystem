package com.hostelmgmt.repository;

import com.hostelmgmt.entity.Bed;
import com.hostelmgmt.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoom(Room room);
    Optional<Bed> findByRoomAndBedNumber(Room room, String bedNumber);
    List<Bed> findByIsOccupiedFalse();
}

