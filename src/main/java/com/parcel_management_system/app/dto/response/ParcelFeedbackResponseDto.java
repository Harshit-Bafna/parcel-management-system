package com.parcel_management_system.app.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParcelFeedbackResponseDto {
    private Integer rating;
    private String suggestion;
}
