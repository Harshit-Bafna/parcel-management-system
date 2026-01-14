package com.parcel_management_system.app.dto.request;

import com.parcel_management_system.app.enums.EDeliveryType;
import com.parcel_management_system.app.enums.EPackagingType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookingParcelRequestDto {
    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be valid")
    private Long customerId;

    @Valid
    @NotNull(message = "Parcel details are required")
    private ParcelRequestDto parcel;

    @Valid
    @NotNull(message = "Receiver details are required")
    private ReceiverDetailsRequestDto receiverDetails;

    @NotNull(message = "Packaging type is required")
    private EPackagingType packagingType;

    @NotNull(message = "Delivery type is required")
    private EDeliveryType deliveryType;

    private String deliveryInstruction;
}
