package com.parcel_management_system.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "parcel")
public class Parcel extends BaseEntity {
    @Column(nullable = false)
    private Double weightInGrams;

    @Column
    private String description;

    @Column
    private LocalDateTime expectedPickupTime;

    @Column
    private LocalDateTime expectedDropofTime;

    @Column
    private LocalDateTime actualPickupTime;

    @Column
    private LocalDateTime actualDropofTime;

    @Column(nullable = false)
    private Double baseRate;

    @Column(nullable = false)
    private Double packagingRate;

    @Column(nullable = false)
    private Double adminFee;

    @Column(nullable = false)
    private Double weightCharge;

    @Column(nullable = false)
    private Double deliveryCharge;

    @Column(nullable = false)
    private Double taxAmount;

    @Column(nullable = false)
    private Double totalCost;
}
