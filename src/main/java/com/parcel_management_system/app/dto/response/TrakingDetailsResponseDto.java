package com.parcel_management_system.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrakingDetailsResponseDto {
    private TrakingSenderDeatilsResponseDto senderDetails;
    private TrakingRecieverDetailsResponseDto recieverDetails;
    private TrakingParcelDetailsResponseDto parcelDeatils;
    private TrakingPaymentDetailsResponseDto paymentDetails;
    private List<TrakingHistoryDetailsResponseDto> history;
}
