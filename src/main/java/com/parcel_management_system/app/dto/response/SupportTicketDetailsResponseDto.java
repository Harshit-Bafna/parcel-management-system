package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.ESupportTicketStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupportTicketDetailsResponseDto {
    private Long ticketId;
    private String bookingId;
    private String title;
    private String description;
    private String response;
    private String createdAt;
    private ESupportTicketStatus status;
}
