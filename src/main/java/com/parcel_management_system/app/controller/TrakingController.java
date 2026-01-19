package com.parcel_management_system.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.response.TrakingDetailsResponseDto;
import com.parcel_management_system.app.service.TrakingService;

@RestController
@RequestMapping("/v1/traking")
public class TrakingController {
    @Autowired
    private TrakingService trakingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<TrakingDetailsResponseDto> getTrakingDetails(@PathVariable String bookingId) {
        TrakingDetailsResponseDto response = trakingService.getTrakingDetails(bookingId);

        return ResponseEntity.ok(response);
    }
}
