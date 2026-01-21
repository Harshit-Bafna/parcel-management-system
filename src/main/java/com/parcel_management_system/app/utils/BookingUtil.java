package com.parcel_management_system.app.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parcel_management_system.app.repository.BookingRepository;

@Component
public class BookingUtil {
    @Autowired
    private BookingRepository bookingRepository;

    public String generateBookingCode() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);

        String trakingId = "BOOK-" + randomNumber;

        while (bookingRepository.existsByTrakingId(trakingId)) {
            randomNumber = 100000 + random.nextInt(900000);
            trakingId = "BOOK-" + randomNumber;
        }

        return trakingId;
    }
}