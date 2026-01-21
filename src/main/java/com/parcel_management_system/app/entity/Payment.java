package com.parcel_management_system.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.parcel_management_system.app.enums.EPaymentMethod;
import com.parcel_management_system.app.enums.EPaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "payment")
public class Payment extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @Column(nullable = false)
    private String last4digits;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private String cardBrand;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private Boolean isRefund;

    @Column
    private Double refundAmount;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Column
    private LocalDateTime refundedAt;
}
