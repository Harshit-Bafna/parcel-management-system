package com.parcel_management_system.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoicePaymentDetailsResponseDto {
    private Double weightInGrams;
    private Double baseRate;
    private Double packagingRate;
    private Double adminFee;
    private Double weightCharge;
    private Double deliveryCharge;
    private Double taxAmount;
    private Double totalCost;
}
