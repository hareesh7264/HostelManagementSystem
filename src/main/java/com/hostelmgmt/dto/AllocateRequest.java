package com.hostelmgmt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllocateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long floorId;
    @NotNull
    private Long roomId;
    @NotNull
    private Long bedId;
}
