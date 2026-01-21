package com.parcel_management_system.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcel_management_system.app.dto.response.TrakingDetailsResponseDto;
import com.parcel_management_system.app.dto.response.TrakingHistoryDetailsResponseDto;
import com.parcel_management_system.app.dto.response.TrakingParcelDetailsResponseDto;
import com.parcel_management_system.app.dto.response.TrakingPaymentDetailsResponseDto;
import com.parcel_management_system.app.dto.response.TrakingRecieverDetailsResponseDto;
import com.parcel_management_system.app.dto.response.TrakingSenderDeatilsResponseDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.entity.CustomerProfile;
import com.parcel_management_system.app.entity.Parcel;
import com.parcel_management_system.app.entity.Payment;
import com.parcel_management_system.app.entity.ReceiverDetails;
import com.parcel_management_system.app.entity.Traking;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.enums.EPaymentStatus;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.CustomerReopository;
import com.parcel_management_system.app.repository.TrakingRepository;

@Service
public class TrakingService {
        @Autowired
        private BookingRepository bookingRepository;

        @Autowired
        private TrakingRepository trakingRepository;

        @Autowired
        private CustomerReopository customerReopository;

        public TrakingDetailsResponseDto getTrakingDetails(String bookingId) {
                Booking booking = bookingRepository.findByTrakingId(bookingId)
                                .orElseThrow(() -> new ResourceNotFound("Booking"));

                Parcel parcelDetails = booking.getParcel();
                ReceiverDetails receiverDetails = booking.getReceiverDetails();
                User senderDetails = booking.getUser();
                Payment paymentDetails = booking.getPayment();
                List<Traking> trakingHistory = trakingRepository.findByBookingId(booking.getId());

                // CustomerProfile senderProfile =
                // customerReopository.findByUserId(senderDetails.getId())
                // .orElseThrow(() -> new ResourceNotFound("User"));

                TrakingSenderDeatilsResponseDto senderDetailsResponse;
                var senderProfileOpt = customerReopository.findByUserId(senderDetails.getId());

                if (senderProfileOpt.isPresent()) {
                        CustomerProfile senderProfile = senderProfileOpt.get();
                        senderDetailsResponse = new TrakingSenderDeatilsResponseDto(
                                        senderDetails.getName(),
                                        senderProfile.getMobileCountryCode(),
                                        senderProfile.getMobileNumber(),
                                        senderDetails.getEmail(),
                                        senderProfile.getHouseNo(),
                                        senderProfile.getAddressLine1(),
                                        senderProfile.getAddressLine2(),
                                        senderProfile.getLandmark(),
                                        senderProfile.getCity(),
                                        senderProfile.getState(),
                                        senderProfile.getPinCode(),
                                        senderProfile.getCountry());
                } else {
                        // Fallback if profile is missing (e.g. legacy/test users)
                        senderDetailsResponse = new TrakingSenderDeatilsResponseDto(
                                        senderDetails.getName(),
                                        "", // Mobile Code
                                        "", // Mobile Number
                                        senderDetails.getEmail(),
                                        "", // House No
                                        "", // Address 1
                                        "", // Address 2
                                        "", // Landmark
                                        "", // City
                                        "", // State
                                        "", // Pincode
                                        ""); // Country
                }

                TrakingRecieverDetailsResponseDto recieverDetailsResponse = new TrakingRecieverDetailsResponseDto(
                                receiverDetails.getName(),
                                receiverDetails.getMobileCountryCode(),
                                receiverDetails.getMobileNumber(),
                                receiverDetails.getAlternateMobileCountryCode(),
                                receiverDetails.getAlternateNumber(),
                                receiverDetails.getEmail(),
                                receiverDetails.getHouseNo(),
                                receiverDetails.getAddressLine1(),
                                receiverDetails.getAddressLine2(),
                                receiverDetails.getLandmark(),
                                receiverDetails.getCity(),
                                receiverDetails.getState(),
                                receiverDetails.getPinCode(),
                                receiverDetails.getCountry());

                TrakingParcelDetailsResponseDto parcelDetailsResponse = new TrakingParcelDetailsResponseDto(
                                booking.getTrakingId(),
                                booking.getBookingStatus(),
                                parcelDetails.getWeightInGrams(),
                                booking.getPackagingType(),
                                booking.getDeliveryType(),
                                booking.getCreatedAt());

                TrakingPaymentDetailsResponseDto paymentResponse = null;

                if (paymentDetails != null) {
                        paymentResponse = new TrakingPaymentDetailsResponseDto(
                                        paymentDetails.getTransactionId(),
                                        paymentDetails.getPaymentStatus(),
                                        paymentDetails.getPaymentMethod(),
                                        paymentDetails.getCardHolderName(),
                                        paymentDetails.getLast4digits(),
                                        paymentDetails.getCardBrand(),
                                        parcelDetails.getBaseRate(),
                                        parcelDetails.getPackagingRate(),
                                        parcelDetails.getAdminFee(),
                                        parcelDetails.getWeightCharge(),
                                        parcelDetails.getDeliveryCharge(),
                                        parcelDetails.getTaxAmount(),
                                        parcelDetails.getTotalCost());
                } else {
                        paymentResponse = new TrakingPaymentDetailsResponseDto(
                                        booking.getTrakingId(),
                                        EPaymentStatus.PENDING,
                                        null,
                                        null,
                                        null,
                                        null,
                                        parcelDetails.getBaseRate(),
                                        parcelDetails.getPackagingRate(),
                                        parcelDetails.getAdminFee(),
                                        parcelDetails.getWeightCharge(),
                                        parcelDetails.getDeliveryCharge(),
                                        parcelDetails.getTaxAmount(),
                                        parcelDetails.getTotalCost());
                }

                List<TrakingHistoryDetailsResponseDto> trakingHistoryResponse = new ArrayList<>();
                for (Traking track : trakingHistory) {
                        EBookingStatus bookingStatus = track.getBookingStatus();
                        LocalDate date = track.getCreatedAt().toLocalDate();
                        String locationCity = track.getLocation();
                        String description = track.getDescription();

                        TrakingHistoryDetailsResponseDto currentHistory = new TrakingHistoryDetailsResponseDto(
                                        bookingStatus, date,
                                        locationCity, description);

                        trakingHistoryResponse.add(currentHistory);
                }

                TrakingDetailsResponseDto response = new TrakingDetailsResponseDto(senderDetailsResponse,
                                recieverDetailsResponse, parcelDetailsResponse, paymentResponse,
                                trakingHistoryResponse);

                return response;
        }

}
