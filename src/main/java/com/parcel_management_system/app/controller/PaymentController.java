package com.parcel_management_system.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.request.ParcelPaymentRequestDto;
import com.parcel_management_system.app.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> payForParcel(@Valid @RequestBody ParcelPaymentRequestDto dto) {
        paymentService.paymentForParcel(dto);

        return ResponseEntity.ok("Payment processed successfully");
    }
}
