package com.parcel_management_system.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrakingSenderDeatilsResponseDto {
    private String name;
    private String mobileCode;
    private String mobileNumber;
    private String email;
    
    private String houseNo;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
    private String country;
}
