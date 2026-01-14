package com.parcel_management_system.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parcel_management_system.app.dto.request.BookingHistoryCustomerRequestDto;
import com.parcel_management_system.app.dto.request.BookingHistoryOfficerRequestDto;
import com.parcel_management_system.app.dto.request.BookingParcelRequestDto;
import com.parcel_management_system.app.dto.request.ParcelRequestDto;
import com.parcel_management_system.app.dto.request.ReceiverDetailsRequestDto;
import com.parcel_management_system.app.dto.response.BookingHistoryCustomerResponseDto;
import com.parcel_management_system.app.dto.response.BookingHistoryOfficerResponseDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.entity.Parcel;
import com.parcel_management_system.app.entity.ReceiverDetails;
import com.parcel_management_system.app.entity.Traking;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.ParcelRepository;
import com.parcel_management_system.app.repository.ReceiverDetailsRepository;
import com.parcel_management_system.app.repository.TrakingRepository;
import com.parcel_management_system.app.repository.UserRepository;
import com.parcel_management_system.app.utils.BookingUtil;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReceiverDetailsRepository receiverDetailsRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingUtil bookingUtil;

    @Autowired
    private TrakingRepository trakingRepository;

    @Transactional
    public void createNewBookingByCustomer(BookingParcelRequestDto dto) {
        ReceiverDetailsRequestDto receiverDetailsDto = dto.getReceiverDetails();
        ParcelRequestDto parcelDto = dto.getParcel();

        User user = userRepository.findById(dto.getCustomerId()).orElseThrow(() -> new ResourceNotFound("Customer"));

        ReceiverDetails receiverDetails = new ReceiverDetails();
        receiverDetails.setUser(user);
        receiverDetails.setEmail(receiverDetailsDto.getEmail());
        receiverDetails.setName(receiverDetailsDto.getName());
        receiverDetails.setMobileCountryCode(receiverDetailsDto.getMobileCountryCode());
        receiverDetails.setMobileNumber(receiverDetailsDto.getMobileNumber());
        receiverDetails.setAlternateMobileCountryCode(receiverDetailsDto.getAlternateMobileCountryCode());
        receiverDetails.setAlternateNumber(receiverDetailsDto.getAlternateNumber());
        receiverDetails.setHouseNo(receiverDetailsDto.getHouseNo());
        receiverDetails.setAddressLine1(receiverDetailsDto.getAddressLine1());
        receiverDetails.setAddressLine2(receiverDetailsDto.getAddressLine2());
        receiverDetails.setLandmark(receiverDetailsDto.getLandmark());
        receiverDetails.setCity(receiverDetailsDto.getCity());
        receiverDetails.setState(receiverDetailsDto.getState());
        receiverDetails.setPinCode(receiverDetailsDto.getPinCode());
        receiverDetails.setCountry(receiverDetails.getCountry());
        ReceiverDetails savedrReceiverDetails = receiverDetailsRepository.save(receiverDetails);

        Parcel parcel = new Parcel();
        parcel.setWeightInGrams(parcelDto.getWeightInGrams());
        parcel.setDescription(parcelDto.getDescription());
        parcel.setExpectedPickupTime(parcelDto.getExpectedPickupTime());
        parcel.setExpectedDropofTime(parcelDto.getExpectedDropofTime());
        parcel.setBaseRate(parcelDto.getBaseRate());
        parcel.setPackagingRate(parcelDto.getPackagingRate());
        parcel.setAdminFee(parcelDto.getAdminFee());
        parcel.setWeightCharge(parcelDto.getWeightCharge());
        parcel.setDeliveryCharge(parcelDto.getDeliveryCharge());
        parcel.setTaxAmount(parcelDto.getTaxAmount());
        parcel.setTotalCost(parcelDto.getTotalCost());
        Parcel savedParcel = parcelRepository.save(parcel);

        Booking booking = new Booking();
        booking.setPackagingType(dto.getPackagingType());
        booking.setDeliveryType(dto.getDeliveryType());
        booking.setTrakingId(bookingUtil.generateBookingCode());
        booking.setUser(user);
        booking.setReceiverDetails(savedrReceiverDetails);
        booking.setParcel(savedParcel);
        booking.setIsPaid(false);
        booking.setPayment(null);
        booking.setBookingStatus(EBookingStatus.BOOKED);
        booking.setDeliveryInstructions(dto.getDeliveryInstruction());
        Booking savedBooking = bookingRepository.save(booking);

        Traking traking = new Traking();
        traking.setBooking(savedBooking);
        traking.setBookingStatus(EBookingStatus.BOOKED);
        traking.setLocation("Parcel Booked");
        traking.setDescription(null);
        trakingRepository.save(traking);

        return;
    }

    public Page<BookingHistoryOfficerResponseDto> getBookingHistoryForOfficer(BookingHistoryOfficerRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("createdAt").descending());

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (dto.getDate() != null) {
            startDate = dto.getDate().atStartOfDay();
            endDate = dto.getDate().plusDays(1).atStartOfDay();
        }

        Page<Booking> bookings = bookingRepository.searchBookingOfficer(
                dto.getBookingStatus(),
                dto.getBookingKeyword(),
                dto.getCustomerKeyword(),
                startDate, endDate, pageable);

        return bookings.map(booking -> new BookingHistoryOfficerResponseDto(
                booking.getId(),
                booking.getTrakingId(),
                booking.getUser().getUsername(),
                booking.getUser().getName(),
                booking.getCreatedAt(),
                booking.getReceiverDetails().getName(),
                booking.getReceiverDetails().getCity() + ", " + booking.getReceiverDetails().getCountry(),
                booking.getParcel().getTotalCost(),
                booking.getBookingStatus()));
    }

    public Page<BookingHistoryCustomerResponseDto> getBookingHistoryForCustomer(BookingHistoryCustomerRequestDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("createdAt").descending());

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (dto.getDate() != null) {
            startDate = dto.getDate().atStartOfDay();
            endDate = dto.getDate().plusDays(1).atStartOfDay();
        }

        Page<Booking> bookings = bookingRepository.searchBookingCustomer(
                dto.getBookingStatus(),
                dto.getBookingKeyword(),
                startDate, endDate, pageable);

        return bookings.map(booking -> new BookingHistoryCustomerResponseDto(
                booking.getId(),
                booking.getTrakingId(),
                booking.getCreatedAt(),
                booking.getReceiverDetails().getName(),
                booking.getReceiverDetails().getCity() + ", " + booking.getReceiverDetails().getCountry(),
                booking.getParcel().getTotalCost(),
                booking.getBookingStatus()));
    }

}
