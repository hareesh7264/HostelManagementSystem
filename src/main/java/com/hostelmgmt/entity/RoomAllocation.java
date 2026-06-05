package com.hostelmgmt.entity;


import com.hostelmgmt.enums.AllocationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "room_allocation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // tenant - reuse your User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    private Floor floor;
    private Integer floorNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    private String roomNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bed_id", nullable = false, unique = true)
    private Bed bed;
    private String bedNumber;


    @Column(nullable = false)
    private LocalDate moveInDate;

    private LocalDate moveOutDate; // null => currently allocated

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllocationStatus status; // ALLOCATED / MOVED_OUT / CANCELLED
}

