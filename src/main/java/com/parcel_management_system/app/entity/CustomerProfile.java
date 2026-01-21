package com.parcel_management_system.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_profile")
public class CustomerProfile extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String mobileCountryCode = "+91";

    @Column(nullable = false)
    private String mobileNumber;

    @Column
    private String alternateMobileCountryCode = "+91";

    @Column
    private String alternateNumber;

    @Column(nullable = false)
    private String houseNo;

    @Column(nullable = false)
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String landmark;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private String country = "India";

    @Column(nullable = false)
    private Integer totalBookings = 0;

    @Column
    private boolean preferences;
}