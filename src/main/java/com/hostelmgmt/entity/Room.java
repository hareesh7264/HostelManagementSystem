package com.hostelmgmt.entity;

import com.hostelmgmt.enums.RoomCategory;
import com.hostelmgmt.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomCategory roomCategory;

    @Column(nullable = false)
    private Double pricePerMonth;

    @Column(nullable = false)
    private Integer totalBeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id")
    private Floor floor;
    private Integer floorNumber;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Bed> beds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
