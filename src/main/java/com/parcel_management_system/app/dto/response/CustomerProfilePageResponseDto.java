package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerProfilePageResponseDto {
    private Long id;
    private String name;
    private String username;
    private ERole role;
    private String email;

    private String mobileCountryCode;
    private String mobileNumber;

    private String alternateMobileCountryCode;
    private String alternateMobileNumber;

    private String houseNo;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
    private String country;

    private boolean preferences;
}