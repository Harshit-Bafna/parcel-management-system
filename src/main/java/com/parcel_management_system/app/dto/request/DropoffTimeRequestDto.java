package com.parcel_management_system.app.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DropoffTimeRequestDto {
    @NotNull(message = "Drop off time is required")
    private LocalDateTime dropoffTime;
}
