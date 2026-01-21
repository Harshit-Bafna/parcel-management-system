package com.parcel_management_system.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.request.LoginRequestDto;
import com.parcel_management_system.app.dto.request.RegisterCustomerRequestDto;
import com.parcel_management_system.app.dto.response.AuthResponseDto;
import com.parcel_management_system.app.dto.response.UserDetailsResponseDto;
import com.parcel_management_system.app.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<UserDetailsResponseDto> registerCustomer(@Valid @RequestBody RegisterCustomerRequestDto dto) {
        UserDetailsResponseDto response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginDTO) {
        AuthResponseDto response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}