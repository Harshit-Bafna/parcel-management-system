package com.parcel_management_system.app.dto.response;

import java.time.LocalDate;

import com.parcel_management_system.app.enums.EBookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrakingHistoryDetailsResponseDto {
    private EBookingStatus bookingStatus;
    private LocalDate date;
    private String locationCity;
    private String description;
}
