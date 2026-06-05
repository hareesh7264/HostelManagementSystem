package com.hostelmgmt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedDto {
    private Long bedId;
    private String bedNumber;
    private Long roomId;
    private String roomNumber;
    private Long assignedUser;    // NULL → bed is empty
    private Boolean isOccupied;
}
