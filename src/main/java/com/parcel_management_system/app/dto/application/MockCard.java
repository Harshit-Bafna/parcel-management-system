package com.parcel_management_system.app.dto.application;

import com.parcel_management_system.app.enums.EPaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MockCard {
    private EPaymentMethod paymentMethod;
    private String cardBrand;
    private String last4digits;
    private String cardHolderName;
}
