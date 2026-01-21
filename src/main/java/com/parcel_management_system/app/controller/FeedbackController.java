package com.parcel_management_system.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parcel_management_system.app.dto.request.ParcelFeedbackRequestDto;
import com.parcel_management_system.app.dto.response.ParcelFeedbackResponseDto;
import com.parcel_management_system.app.service.FeedbackService;

@RestController
@RequestMapping("/v1/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<String> addFeedback(@PathVariable Long bookingId, @RequestBody ParcelFeedbackRequestDto dto) {
        feedbackService.addFeedback(dto, bookingId);

        return ResponseEntity.ok("Feedback added successfully");
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ParcelFeedbackResponseDto> getFeedback(@PathVariable Long bookingId) {
        ParcelFeedbackResponseDto respone = feedbackService.getParcelFeedback(bookingId);

        return ResponseEntity.ok(respone);
    }
}
