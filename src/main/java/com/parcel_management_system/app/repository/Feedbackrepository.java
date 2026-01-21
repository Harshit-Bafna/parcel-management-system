package com.parcel_management_system.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcel_management_system.app.entity.Feedback;

@Repository
public interface Feedbackrepository extends JpaRepository<Feedback, Long> {

    Optional<Feedback> findByBookingId(Long bookingId);
}
