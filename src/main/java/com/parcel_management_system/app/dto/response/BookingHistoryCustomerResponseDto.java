package com.parcel_management_system.app.dto.response;

import java.time.LocalDateTime;

import com.parcel_management_system.app.enums.EBookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingHistoryCustomerResponseDto {
    private Long id;
    private String trakingId;
    private LocalDateTime bookingDate;
    private String receiverName;
    private String deliveryAddress;
    private Double amount;
    private EBookingStatus bookingStatus;
}
