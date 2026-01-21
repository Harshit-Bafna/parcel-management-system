package com.parcel_management_system.app.dto.request;

import java.time.LocalDate;

import com.parcel_management_system.app.enums.EPaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParcelPaymentRequestDto {
    @NotNull(message = "Booking ID is required")
    private String bookingId;

    @NotNull(message = "Payment method is required")
    private EPaymentMethod paymentMethod;

    @NotBlank(message = "Last 4 digits of card are required")
    @Pattern(regexp = "\\d{4}", message = "Last 4 digits must contain exactly 4 numeric digits")
    private String last4digits;

    @NotBlank(message = "Card brand is required")
    @Size(min = 2, max = 20, message = "Card brand must be between 2 and 20 characters")
    private String cardBrand;

    @NotBlank(message = "Card holder name is required")
    @Size(min = 3, max = 50, message = "Card holder name must be between 3 and 50 characters")
    private String cardHolderName;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;
}
