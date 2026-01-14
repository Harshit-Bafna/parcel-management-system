package com.parcel_management_system.app.entity;

import com.parcel_management_system.app.enums.EBookingStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "traking")
public class Traking extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "bookingId", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EBookingStatus bookingStatus;

    @Column(nullable = false)
    private String location;

    @Column
    private String description;
}
