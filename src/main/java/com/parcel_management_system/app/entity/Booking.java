package com.parcel_management_system.app.entity;

import com.parcel_management_system.app.enums.EBookingStatus;
import com.parcel_management_system.app.enums.EDeliveryType;
import com.parcel_management_system.app.enums.EPackagingType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "booking")
public class Booking extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPackagingType packagingType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EDeliveryType deliveryType;

    @Column(nullable = false, unique = true)
    private String trakingId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "receiverId", nullable = false, unique = true)
    private ReceiverDetails receiverDetails;

    @OneToOne
    @JoinColumn(name = "parcelId", nullable = false, unique = true)
    private Parcel parcel;

    @Column
    private Boolean isPaid;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EBookingStatus bookingStatus;

    @Column
    private String deliveryInstructions;

    @Column
    private Long officerId;
}
