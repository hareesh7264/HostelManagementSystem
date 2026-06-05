package com.hostelmgmt.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveOutRequest {
    @NotNull
    private Long allocationId;
}

