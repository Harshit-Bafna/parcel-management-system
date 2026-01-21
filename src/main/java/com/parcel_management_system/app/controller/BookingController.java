package com.parcel_management_system.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.request.BookingHistoryCustomerRequestDto;
import com.parcel_management_system.app.dto.request.BookingHistoryOfficerRequestDto;
import com.parcel_management_system.app.dto.request.BookingParcelRequestDto;
import com.parcel_management_system.app.dto.request.DropoffTimeRequestDto;
import com.parcel_management_system.app.dto.request.PickupTimeRequestDto;
import com.parcel_management_system.app.dto.response.BookingHistoryCustomerResponseDto;
import com.parcel_management_system.app.dto.response.BookingHistoryOfficerResponseDto;
import com.parcel_management_system.app.dto.response.BookingResponseDto;
import com.parcel_management_system.app.dto.response.InvoiceDetailsResponseDto;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.security.JwtUtil;
import com.parcel_management_system.app.service.BookingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create/customer")
    public ResponseEntity<BookingResponseDto> createBooking(@Valid @RequestBody BookingParcelRequestDto dto,
            HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        String role = jwtUtil.extractRole(token);
        Long officerId = null;

        if ("OFFICER".equals(role)) {
            officerId = jwtUtil.extractId(token);
        }

        BookingResponseDto response = bookingService.createNewBookingByCustomer(dto, officerId);

        return ResponseEntity.ok(response);
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
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            HttpServletRequest request) {

        BookingHistoryCustomerRequestDto dto = new BookingHistoryCustomerRequestDto(bookingStatus, bookingKeyword, date,
                page, size);

        String token = jwtUtil.extractToken(request);
        Long customerId = jwtUtil.extractId(token);

        System.out.println("customerId" + customerId);

        Page<BookingHistoryCustomerResponseDto> response = bookingService.getBookingHistoryForCustomer(dto, customerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoice/{bookingId}")
    public ResponseEntity<InvoiceDetailsResponseDto> getInvoiceDetails(@PathVariable Long bookingId) {
        InvoiceDetailsResponseDto response = bookingService.getInvoiceDetails(bookingId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/set/pickUp-time/{bookingId}")
    public ResponseEntity<String> setPickupTime(@Valid @RequestBody PickupTimeRequestDto dto,
            @PathVariable String bookingId) {
        bookingService.setPickupTime(dto, bookingId);

        return ResponseEntity.ok("Pick up set successfully");
    }

    @PatchMapping("/set/dropoff-time/{bookingId}")
    public ResponseEntity<String> setDropoffTime(@Valid @RequestBody DropoffTimeRequestDto dto,
            @PathVariable String bookingId) {
        bookingService.setDropoffTime(dto, bookingId);

        return ResponseEntity.ok("Parcel delivered successfully");
    }

    @PatchMapping("/{bookingId}/in-transit")
    public ResponseEntity<String> parcelInTransit(@PathVariable String bookingId) {
        bookingService.parcelInTransit(bookingId);
        return ResponseEntity.ok("Parcel is now in transit");
    }

    @PatchMapping("/{bookingId}/out-for-delivery")
    public ResponseEntity<String> outForDelivery(@PathVariable String bookingId) {
        bookingService.outForDelivery(bookingId);
        return ResponseEntity.ok("Parcel is out for delivery");
    }

    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable String bookingId) {
        bookingService.cancelOrder(bookingId);
        return ResponseEntity.ok("Booking cancelled successfully");
    }

    @PatchMapping("/{bookingId}/failed-delivery")
    public ResponseEntity<String> failedDelivery(@PathVariable String bookingId) {
        bookingService.failedDelivery(bookingId);
        return ResponseEntity.ok("Delivery failed and booking updated");
    }

}
