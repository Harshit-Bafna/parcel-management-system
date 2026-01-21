package com.parcel_management_system.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerHomePageResponseDto {
    private Long id;
    private String name;
    private Long totalBookings;
    private Long ongoingBookings;
    private Long completedBookings;
    private Long cancelledBookings;
}