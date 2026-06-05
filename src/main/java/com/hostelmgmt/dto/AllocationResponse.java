package com.hostelmgmt.dto;

import com.hostelmgmt.enums.AllocationStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocationResponse {
    private Long allocationId;
    private Long userId;
    private String userName;

    private Long floorId;
    private Integer floorNumber;

    private Long roomId;
    private String roomNumber;

    private Long bedId;
    private String bedNumber;

    private LocalDate moveInDate;
    private LocalDate moveOutDate;

    private AllocationStatus status;
}
