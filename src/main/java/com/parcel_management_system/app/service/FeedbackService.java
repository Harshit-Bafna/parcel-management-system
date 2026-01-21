package com.parcel_management_system.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parcel_management_system.app.dto.request.ParcelFeedbackRequestDto;
import com.parcel_management_system.app.dto.response.ParcelFeedbackResponseDto;
import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.entity.Feedback;
import com.parcel_management_system.app.entity.OfficerProfile;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.exception.CustomException;
import com.parcel_management_system.app.exception.ResourceNotFound;
import com.parcel_management_system.app.repository.BookingRepository;
import com.parcel_management_system.app.repository.Feedbackrepository;
import com.parcel_management_system.app.repository.OfficerRepository;
import com.parcel_management_system.app.repository.UserRepository;

@Service
public class FeedbackService {
    @Autowired
    private Feedbackrepository feedbackrepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void addFeedback(ParcelFeedbackRequestDto dto, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Booking"));

        if (booking.getBookingStatus() != EBookingStatus.DELIVERED) {
            throw new CustomException("Order not delivered", HttpStatus.BAD_REQUEST);
        }

        System.out.println("rating: " + dto);

        User user = userRepository.findUserByUsername("OFCR-100000").orElseThrow(() -> new ResourceNotFound("Officer"));
        OfficerProfile officerProfile = officerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFound("Officer"));

        Feedback feedback = new Feedback();
        feedback.setRating(dto.getRating());
        feedback.setSuggestions(dto.getSuggestion());
        feedback.setBooking(booking);
        feedbackrepository.save(feedback);

        Double updatedRating = (officerProfile.getRating() * officerProfile.getRatedBy() + dto.getRating())
                / (officerProfile.getRatedBy() + 1);
        officerProfile.setRatedBy(officerProfile.getRatedBy() + 1);
        officerProfile.setRating(updatedRating);
        officerRepository.save(officerProfile);
    }

    public ParcelFeedbackResponseDto getParcelFeedback(Long bookingId) {
        Feedback feedback = feedbackrepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFound("Feedback"));

        ParcelFeedbackResponseDto response = new ParcelFeedbackResponseDto(feedback.getRating(),
                feedback.getSuggestions());

        return response;
    }
}
