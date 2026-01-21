package com.parcel_management_system.app.utils;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parcel_management_system.app.enums.ERole;
import com.parcel_management_system.app.repository.UserRepository;

@Component
public class AuthUtil {
    @Autowired
    private UserRepository userRepository;

    public String generateUserCode(ERole role) {
        String prefix;
        switch (role) {
            case CUSTOMER:
                prefix = "CUST-";
                break;
            case OFFICER:
                prefix = "OFCR-";
                break;
            case SUPPORT:
                prefix = "SUPP-";
                break;
            default:
                prefix = "USER-";
        }

        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);

        String username = prefix + randomNumber;

        while (userRepository.existsByUsername(username)) {
            randomNumber = 100000 + random.nextInt(900000);
            username = prefix + randomNumber;
        }

        return username;
    }
}