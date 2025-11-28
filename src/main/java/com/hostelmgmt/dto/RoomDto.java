package com.hostelmgmt.dto;

import com.hostelmgmt.enums.RoomCategory;
import com.hostelmgmt.enums.RoomType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

    private Long roomId;
    private String roomNumber;
    private RoomType roomType;
    private RoomCategory roomCategory;
    private Double pricePerMonth;
    private Integer totalBeds;

    private Integer floorNumber;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Optional: List of bed numbers
    private List<String> beds;
}
