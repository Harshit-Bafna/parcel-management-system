package com.parcel_management_system.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcel_management_system.app.dto.request.SupportTicketClosureRequestDto;
import com.parcel_management_system.app.dto.request.SupportTicketRequestDto;
import com.parcel_management_system.app.dto.response.SupportTicketDetailsResponseDto;
import com.parcel_management_system.app.service.SupportTicketService;

@RestController
@RequestMapping("/v1/support")
public class SupportTicketController {

    @Autowired
    private SupportTicketService supportTicketService;

    @PostMapping("/raise/{bookingId}")
    public ResponseEntity<Long> raiseSupportTicket(
            @PathVariable String bookingId,
            @RequestBody SupportTicketRequestDto dto) {

        System.out.println(bookingId + " " + dto);
        Long ticketId = supportTicketService.raiseSupportTicket(dto, bookingId);
        return new ResponseEntity<>(ticketId, HttpStatus.CREATED);
    }

    @PutMapping("/close/{ticketId}")
    public ResponseEntity<String> closeSupportTicket(
            @PathVariable Long ticketId,
            @RequestBody SupportTicketClosureRequestDto dto) {

        System.out.println("Here");
        supportTicketService.closeSupportTicket(dto, ticketId);
        return ResponseEntity.ok("Support ticket closed successfully");
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<SupportTicketDetailsResponseDto> getSupportTicket(
            @PathVariable String ticketId) {

        SupportTicketDetailsResponseDto response = supportTicketService.getSupportTicket(ticketId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupportTicketDetailsResponseDto>> getAllSupportTickets() {
        return ResponseEntity.ok(supportTicketService.getSupportTicketList());
    }
}
