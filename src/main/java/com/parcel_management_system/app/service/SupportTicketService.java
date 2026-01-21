package com.parcel_management_system.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.parcel_management_system.app.dto.request.SupportTicketClosureRequestDto;
import com.parcel_management_system.app.dto.request.SupportTicketRequestDto;
import com.parcel_management_system.app.dto.response.SupportTicketDetailsResponseDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.entity.SupportTicket;
import com.parcel_management_system.app.enums.ESupportTicketStatus;
import com.parcel_management_system.app.exception.CustomException;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.SupportTicketRepository;

@Service
public class SupportTicketService {
    @Autowired
    private SupportTicketRepository supportTicketRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Long raiseSupportTicket(SupportTicketRequestDto dto, String bookingId) {
        Booking booking = bookingRepository.findByTrakingId(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking"));

        System.out.print("I am here" + bookingId);
        Boolean isAvialable = supportTicketRepository.existsByBookingId(booking.getId());
        if (isAvialable) {
            System.out.print("I am here too " + bookingId);
            throw new CustomException("Ticket already generated for this booking", HttpStatus.BAD_REQUEST);
        }
        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setBooking(booking);
        supportTicket.setTitle(dto.getTitle());
        supportTicket.setDescription(dto.getDescription());
        supportTicket.setStatus(ESupportTicketStatus.OPEN);
        SupportTicket savedTicket = supportTicketRepository.save(supportTicket);
        return savedTicket.getId();
    }

    public void closeSupportTicket(SupportTicketClosureRequestDto dto, Long ticketId) {
        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFound("Support ticket"));

        supportTicket.setStatus(ESupportTicketStatus.CLOSED);
        supportTicket.setResponse(dto.getResponse());
        supportTicketRepository.save(supportTicket);
    }

    public SupportTicketDetailsResponseDto getSupportTicket(String ticketId) {
        SupportTicket ticket = supportTicketRepository.findById(Long.parseLong(ticketId))
                .orElseThrow(() -> new ResourceNotFound("Support ticket"));

        return new SupportTicketDetailsResponseDto(
                ticket.getId(),
                ticket.getBooking().getTrakingId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getResponse(),
                ticket.getCreatedAt() != null ? ticket.getCreatedAt().toString() : null,
                ticket.getStatus());
    }

    public List<SupportTicketDetailsResponseDto> getSupportTicketList() {
        List<SupportTicket> tickets = supportTicketRepository.findAll();

        List<SupportTicketDetailsResponseDto> responses = new ArrayList<>();
        for (SupportTicket ticket : tickets) {
            SupportTicketDetailsResponseDto response = new SupportTicketDetailsResponseDto(
                    ticket.getId(),
                    ticket.getBooking().getTrakingId(),
                    ticket.getTitle(),
                    ticket.getDescription(),
                    ticket.getResponse(),
                    ticket.getCreatedAt() != null ? ticket.getCreatedAt().toString() : null,
                    ticket.getStatus());

            responses.add(response);
        }

        return responses;
    }
}
