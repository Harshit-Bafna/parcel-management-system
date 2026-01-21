package com.parcel_management_system.app.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParcelRequestDto {
    @NotNull(message = "Parcel weight details is required")
    @Positive(message = "Parcel weight must be positive")
    private Double weightInGrams;

    @Size(min = 10, max = 200, message = "Name must be between 2 and 200 characters")
    private String description;

    @FutureOrPresent(message = "Pickup time cannot be in past")
    private LocalDateTime expectedPickupTime;

    @FutureOrPresent(message = "Drop Of time cannot be in past")
    private LocalDateTime expectedDropofTime;

    @NotNull(message = "Base rate is required")
    @PositiveOrZero(message = "Base rate must be positive or zero")
    private Double baseRate;

    @NotNull(message = "Packaging rate is required")
    @PositiveOrZero(message = "Packaging rate must be positive or zero")
    private Double packagingRate;

    @NotNull(message = "Admin fee is required")
    @PositiveOrZero(message = "Admin fee must be positive or zero")
    private Double adminFee;

    @NotNull(message = "Weight charge is required")
    @PositiveOrZero(message = "Weight charge must be positive or zero")
    private Double weightCharge;

    @NotNull(message = "Delivery charge is required")
    @PositiveOrZero(message = "Delivery charge must be positive or zero")
    private Double deliveryCharge;

    @NotNull(message = "Tax amount is required")
    @PositiveOrZero(message = "Tax amount must be positive or zero")
    private Double taxAmount;

    @NotNull(message = "Total cost is required")
    @PositiveOrZero(message = "Total cost must be positive or zero")
    private Double totalCost;
}
