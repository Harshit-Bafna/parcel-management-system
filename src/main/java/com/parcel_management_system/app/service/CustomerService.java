package com.parcel_management_system.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcel_management_system.app.dto.response.CustomerHomePageResponseDto;
import com.parcel_management_system.app.dto.response.CustomerListResponseDto;
import com.parcel_management_system.app.dto.response.CustomerProfilePageResponseDto;
import com.parcel_management_system.app.dto.response.OfficerHomePageResponseDto;
import com.parcel_management_system.app.entity.CustomerProfile;
import com.parcel_management_system.app.entity.OfficerProfile;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.ERole;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.CustomerReopository;
import com.parcel_management_system.app.repository.OfficerRepository;
import com.parcel_management_system.app.repository.UserRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerReopository customerReopository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private OfficerRepository officerRepository;

    public CustomerHomePageResponseDto getCustomerHomePageDetails(Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow(() -> new ResourceNotFound("Customer"));

        Long totalBookings = bookingRepository.countCustomerBooking(customerId);
        Long deliveredBooking = bookingRepository.countCustomeDeliveredrBooking(customerId);
        Long cancelledBookings = bookingRepository.countCustomerCancelledBooking(customerId);
        Long countOngoingBookings = totalBookings - deliveredBooking - cancelledBookings;

        return new CustomerHomePageResponseDto(
                customerId,
                customer.getName(),
                totalBookings,
                countOngoingBookings,
                deliveredBooking,
                cancelledBookings);
    }

    public OfficerHomePageResponseDto getOfficerHomePageDetails(Long officerId) {
        User officer = userRepository.findById(officerId).orElseThrow(() -> new ResourceNotFound("Officer"));
        OfficerProfile officerProfile = officerRepository.findByUserId(officerId)
                .orElseThrow(() -> new ResourceNotFound("Officer"));

        Long totalBookings = bookingRepository.countOfficerBooking(officerId);
        Long deliveredBooking = bookingRepository.countOfficerDeliveredrBooking(officerId);
        Long cancelledBookings = bookingRepository.countOfficerCancelledBooking(officerId);
        Long countOngoingBookings = totalBookings - deliveredBooking - cancelledBookings;

        return new OfficerHomePageResponseDto(
                officerId,
                officer.getName(),
                totalBookings,
                countOngoingBookings,
                deliveredBooking,
                cancelledBookings,
                officerProfile.getRating());
    }

    public CustomerProfilePageResponseDto getCustomerProfileDetails(Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow(() -> new ResourceNotFound("Customer"));
        CustomerProfile customerProfile = customerReopository.findByUserId(customerId)
                .orElseThrow(() -> new ResourceNotFound("Customer"));

        return new CustomerProfilePageResponseDto(
                customerId,
                customer.getName(),
                customer.getUsername(),
                customer.getRole(),
                customer.getEmail(),
                customerProfile.getMobileCountryCode(),
                customerProfile.getMobileNumber(),
                customerProfile.getAlternateMobileCountryCode(),
                customerProfile.getAlternateNumber(),
                customerProfile.getHouseNo(),
                customerProfile.getAddressLine1(),
                customerProfile.getAddressLine2(),
                customerProfile.getLandmark(),
                customerProfile.getCity(),
                customerProfile.getState(),
                customerProfile.getPinCode(),
                customerProfile.getCountry(),
                customerProfile.isPreferences());
    }

    public List<CustomerListResponseDto> getCustomerList() {
        List<User> customers = userRepository.findAllByRole(ERole.CUSTOMER);

        List<CustomerListResponseDto> responses = new ArrayList<>();
        for (User customer : customers) {
            responses.add(new CustomerListResponseDto(
                    customer.getId(),
                    customer.getUsername()));
        }

        return responses;
    }
}