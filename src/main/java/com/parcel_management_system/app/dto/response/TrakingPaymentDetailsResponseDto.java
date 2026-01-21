package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.EPaymentMethod;
import com.parcel_management_system.app.enums.EPaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrakingPaymentDetailsResponseDto {
    private String transactionId;
    private EPaymentStatus paymentStatus;
    private EPaymentMethod paymentMethod;
    private String cardHolderName;
    private String last4Digits;
    private String cardBrand;

    private Double baseRate;
    private Double packagingRate;
    private Double adminFee;
    private Double weightCharge;
    private Double deliveryCharge;
    private Double taxAmount;
    private Double totalCost;
}
