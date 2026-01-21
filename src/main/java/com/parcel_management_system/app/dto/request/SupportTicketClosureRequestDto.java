package com.parcel_management_system.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupportTicketClosureRequestDto {
    @NotBlank(message = "Response must not be empty")
    @Size(min = 10, max = 1000, message = "Response must be between 10 and 1000 characters")
    private String response;
}
