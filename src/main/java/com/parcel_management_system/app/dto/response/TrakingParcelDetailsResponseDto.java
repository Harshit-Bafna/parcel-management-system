package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.enums.EDeliveryType;
import com.parcel_management_system.app.enums.EPackagingType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrakingParcelDetailsResponseDto {
    private String bookingId;
    private EBookingStatus bookingStatus;
    private Double weight;
    private EPackagingType packagingType;
    private EDeliveryType deliveryType;
}
