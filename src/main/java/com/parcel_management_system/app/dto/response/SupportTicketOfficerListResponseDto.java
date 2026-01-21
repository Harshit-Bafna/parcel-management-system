package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.ESupportTicketStatus;

import lombok.Data;

@Data
public class SupportTicketOfficerListResponseDto {
    private Long id;
    private String customerId;
    private String bookingId;
    private String title;
    private String description;
    private ESupportTicketStatus status;
}
