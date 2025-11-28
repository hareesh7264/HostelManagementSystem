package com.hostelmgmt.repository;

import com.hostelmgmt.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
    boolean existsByFloorName(String name);
    boolean existsByFloorNumber(Integer number);

    Optional<Floor> findByFloorNumber(Integer floorNumber);

}

