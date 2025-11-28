package com.hostelmgmt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SwitchBedRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long newBedId;

}
