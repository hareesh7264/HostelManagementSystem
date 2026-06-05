package com.hostelmgmt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "floors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId;

    @Column(nullable = false, unique = true)
    private String floorName;

    @Column(nullable = false)
    private Integer floorNumber;

    @Column(nullable = false)
    private Integer totalRooms;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<Room> rooms;

    private Boolean isActive;
}

