package com.parcel_management_system.app.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parcel_management_system.app.entity.Booking;
import com.parcel_management_system.app.enums.EBookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByTrakingId(String trakingId);

    @Query("""
            SELECT b FROM Booking b
            WHERE (:bookingStatus IS NULL OR b.bookingStatus = :bookingStatus)
                AND (:bookingKeyword IS NULL OR LOWER(b.trakingId) LIKE (CONCAT('%', :bookingKeyword, '%')))
                AND (:customerKeyword IS NULL OR CAST(b.user.id AS string) LIKE (CONCAT('%', :customerKeyword, '%')))
                AND (:startDate IS NULL OR b.createdAt >= :startDate)
                AND (:endDate IS NULL OR b.createdAt <= :endDate)
            """)
    Page<Booking> searchBookingOfficer(
            @Param("bookingStatus") EBookingStatus bookingStatus,
            @Param("bookingKeyword") String bookingKeyword,
            @Param("customerKeyword") String customerKeyword,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            WHERE (:bookingStatus IS NULL OR b.bookingStatus = :bookingStatus)
                AND (:customerId IS NULL OR b.user.id = :customerId)
                AND (:bookingKeyword IS NULL OR LOWER (b.trakingId) LIKE (CONCAT('%', :bookingKeyword, '%')))
                AND (:startDate IS NULL OR b.createdAt >= :startDate)
                AND (:endDate IS NULL OR b.createdAt <= :endDate)
            """)
    Page<Booking> searchBookingCustomer(
            @Param("bookingStatus") EBookingStatus bookingStatus,
            @Param("bookingKeyword") String bookingKeyword,
            @Param("customerId") Long customerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    Optional<Booking> findByTrakingId(String bookingId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE (b.user.id = :customerId)
            """)
    Long countCustomerBooking(Long customerId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE (b.user.id = :customerId)
                AND (b.bookingStatus = 'CANCELLED')
            """)
    Long countCustomerCancelledBooking(Long customerId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE (b.user.id = :customerId)
                AND (b.bookingStatus = 'DELIVERED')
            """)
    Long countCustomeDeliveredrBooking(Long customerId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            """)
    Long countOfficerBooking(Long customerId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE (b.bookingStatus = 'CANCELLED')
            """)
    Long countOfficerCancelledBooking(Long customerId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE (b.bookingStatus = 'DELIVERED')
            """)
    Long countOfficerDeliveredrBooking(Long customerId);
}
