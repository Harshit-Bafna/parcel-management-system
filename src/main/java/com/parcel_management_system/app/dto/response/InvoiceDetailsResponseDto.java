package com.parcel_management_system.app.dto.response;

import java.time.LocalDateTime;

import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.enums.EPaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceDetailsResponseDto {
    private String bookingNumber;
    private String customerUsername;
    private LocalDateTime bookingTime;
    private String receiverName;
    private String deliveryAddress;
    private EBookingStatus bookingStatus;
    private EPaymentStatus paymentStatus;
    private InvoicePaymentDetailsResponseDto parcelDetails;
    private String transactionId;
}
