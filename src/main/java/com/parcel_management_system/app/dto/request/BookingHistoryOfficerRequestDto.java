package com.parcel_management_system.app.dto.request;

import java.time.LocalDate;

import com.parcel_management_system.app.enums.EBookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingHistoryOfficerRequestDto {
    private EBookingStatus bookingStatus;
    private String bookingKeyword;
    private String customerKeyword;
    private LocalDate date;
    private int page = 0;
    private int size = 10;
}
