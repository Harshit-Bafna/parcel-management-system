package com.parcel_management_system.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.request.BookingHistoryCustomerRequestDto;
import com.parcel_management_system.app.dto.request.BookingHistoryOfficerRequestDto;
import com.parcel_management_system.app.dto.request.BookingParcelRequestDto;
import com.parcel_management_system.app.dto.response.BookingHistoryCustomerResponseDto;
import com.parcel_management_system.app.dto.response.BookingHistoryOfficerResponseDto;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.service.BookingService;

import jakarta.validation.Valid;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Void> createBooking(@Valid @RequestBody BookingParcelRequestDto dto) {
        bookingService.createNewBookingByCustomer(dto);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/history/officer")
    public ResponseEntity<Page<BookingHistoryOfficerResponseDto>> getBookingHistoryForOfficer(
            @RequestParam(name = "bookingStatus", required = false) EBookingStatus bookingStatus,
            @RequestParam(name = "bookingKeyword", required = false) String bookingKeyword,
            @RequestParam(name = "customerKeyword", required = false) String customerKeyword,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        BookingHistoryOfficerRequestDto dto = new BookingHistoryOfficerRequestDto(bookingStatus, bookingKeyword,
                customerKeyword, date, page, size);

        Page<BookingHistoryOfficerResponseDto> response = bookingService.getBookingHistoryForOfficer(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/customer")
    public ResponseEntity<Page<BookingHistoryCustomerResponseDto>> getBookingHistoryForCustomer(
            @RequestParam(name = "bookingStatus", required = false) EBookingStatus bookingStatus,
            @RequestParam(name = "bookingKeyword", required = false) String bookingKeyword,
            @RequestParam(name = "date", required = false) LocalDate date,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        BookingHistoryCustomerRequestDto dto = new BookingHistoryCustomerRequestDto(bookingStatus, bookingKeyword, date,
                page, size);

        Page<BookingHistoryCustomerResponseDto> response = bookingService.getBookingHistoryForCustomer(dto);

        return ResponseEntity.ok(response);
    }

}
