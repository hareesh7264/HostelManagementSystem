package com.hostelmgmt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorDto {
    private Long floorId;
    private String floorName;
    private Integer floorNumber;
    private Integer totalRooms;
    private Boolean isActive;
}
