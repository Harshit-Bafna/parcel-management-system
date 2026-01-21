package com.parcel_management_system.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.response.CustomerHomePageResponseDto;
import com.parcel_management_system.app.dto.response.CustomerListResponseDto;
import com.parcel_management_system.app.dto.response.CustomerProfilePageResponseDto;
import com.parcel_management_system.app.dto.response.OfficerHomePageResponseDto;
import com.parcel_management_system.app.service.CustomerService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/details/home-page/{customerId}")
    public ResponseEntity<CustomerHomePageResponseDto> getCustomerHomePageDetails(@PathVariable Long customerId) {
        CustomerHomePageResponseDto response = customerService.getCustomerHomePageDetails(customerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/officer/details/home-page/{officerId}")
    public ResponseEntity<OfficerHomePageResponseDto> getOfficerHomePageDetails(@PathVariable Long officerId) {
        OfficerHomePageResponseDto response = customerService.getOfficerHomePageDetails(officerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/details/profile-page/{customerId}")
    public ResponseEntity<CustomerProfilePageResponseDto> getCustomerProfilePageDetails(@PathVariable Long customerId) {
        CustomerProfilePageResponseDto response = customerService.getCustomerProfileDetails(customerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CustomerListResponseDto>> getCustomerList() {
        List<CustomerListResponseDto> response = customerService.getCustomerList();

        return ResponseEntity.ok(response);
    }

}