package com.parcel_management_system.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.parcel_management_system.app.entity.CustomerProfile;
import com.parcel_management_system.app.entity.OfficerProfile;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.ERole;
import com.parcel_management_system.app.repository.CustomerReopository;
import com.parcel_management_system.app.repository.OfficerRepository;
import com.parcel_management_system.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private CustomerReopository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        String encodedPassword = passwordEncoder.encode("Password@123");

        User officer = userRepository.save(
                new User(ERole.OFFICER, "Rajesh Officer",
                        "OFCR-100000", "officer1@gmail.com",
                        encodedPassword, null));

        User customer1 = userRepository.save(
                new User(ERole.CUSTOMER, "Amit Customer",
                        "CUST-100000", "customer1@gmail.com",
                        encodedPassword, null));

        User customer2 = userRepository.save(
                new User(ERole.CUSTOMER, "Neha Customer",
                        "CUST-100001", "customer2@gmail.com",
                        encodedPassword, null));

        User customer3 = userRepository.save(
                new User(ERole.CUSTOMER, "Rahul Customer",
                        "CUST-100002", "customer3@gmail.com",
                        encodedPassword, null));

        userRepository.saveAll(List.of(
                new User(ERole.SUPPORT, "Support One",
                        "SUPP-100000", "support1@gmail.com",
                        encodedPassword, null),
                new User(ERole.SUPPORT, "Support Two",
                        "SUPP-100001", "support2@gmail.com",
                        encodedPassword, null)));

        officerRepository.save(
                new OfficerProfile(officer, 0, 0.0));

        customerRepository.saveAll(List.of(
                new CustomerProfile(customer1, "+91", "9876543210",
                        "+91", null, "12A", "MG Road", null,
                        "Near Mall", "Bangalore", "Karnataka",
                        "560001", "India", 0, false),

                new CustomerProfile(customer2, "+91", "9876543222",
                        "+91", null, "22B", "Ring Road", null,
                        "Bus Stand", "Delhi", "Delhi",
                        "110001", "India", 0, true),

                new CustomerProfile(customer3, "+91", "9876543333",
                        "+91", null, "9C", "Park Street", null,
                        "Metro Station", "Kolkata", "West Bengal",
                        "700001", "India", 0, false)));
    }
}
