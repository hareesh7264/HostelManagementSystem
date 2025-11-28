package com.hostelmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BedDto {
    private Long bedId;
    private String bedNumber;
    private Long roomId;
    private Long assignedUser;    // NULL → bed is empty
    private Boolean isOccupied;
}
