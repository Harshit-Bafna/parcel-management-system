package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.ESupportTicketStatus;

import lombok.Data;

@Data
public class SupportTicketCustomerListResponseDto {
    private Long id;
    private String bookingId;
    private String title;
    private String description;
    private ESupportTicketStatus status;
}
