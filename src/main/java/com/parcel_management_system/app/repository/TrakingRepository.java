package com.parcel_management_system.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parcel_management_system.app.entity.Traking;

@Repository
public interface TrakingRepository extends JpaRepository<Traking, Long> {
    List<Traking> findByBookingId(Long bookingId);
}
