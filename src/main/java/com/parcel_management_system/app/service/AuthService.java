package com.parcel_management_system.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parcel_management_system.app.dto.request.LoginRequestDto;
import com.parcel_management_system.app.dto.request.RegisterCustomerRequestDto;
import com.parcel_management_system.app.dto.response.AuthResponseDto;
import com.parcel_management_system.app.dto.response.UserDetailsResponseDto;
import com.parcel_management_system.app.entity.CustomerProfile;
import com.parcel_management_system.app.entity.User;
import com.parcel_management_system.app.enums.ERole;
import com.parcel_management_system.app.exception.CustomException;
import com.parcel_management_system.app.exception.ResourceAlreadyExists;
import com.parcel_management_system.app.repository.CustomerReopository;
import com.parcel_management_system.app.repository.UserRepository;
import com.parcel_management_system.app.security.JwtUtil;
import com.parcel_management_system.app.utils.AuthUtil;

@Service
public class AuthService {
    @Autowired
    private CustomerReopository customerReopository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    AuthService(CustomerReopository customerReopository) {
        this.customerReopository = customerReopository;
    }

    @Transactional
    public UserDetailsResponseDto register(RegisterCustomerRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResourceAlreadyExists("User");
        }

        User user = new User();
        user.setRole(ERole.CUSTOMER);
        user.setUsername(authUtil.generateUserCode(ERole.CUSTOMER));
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());

        User savedUser = userRepository.save(user);

        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setUser(savedUser);
        customerProfile.setMobileCountryCode(dto.getMobileCountryCode());
        customerProfile.setMobileNumber(dto.getMobileNumber());
        customerProfile.setAlternateMobileCountryCode(dto.getAlternateMobileCountryCode());
        customerProfile.setAlternateNumber(dto.getAlternateMobileNumber());
        customerProfile.setHouseNo(dto.getHouseNo());
        customerProfile.setAddressLine1(dto.getAddressLine1());
        customerProfile.setAddressLine2(dto.getAddressLine2());
        customerProfile.setLandmark(dto.getLandmark());
        customerProfile.setCity(dto.getCity());
        customerProfile.setState(dto.getState());
        customerProfile.setPinCode(dto.getPinCode());
        customerProfile.setCountry(dto.getCountry());
        customerProfile.setTotalBookings(0);
        customerProfile.setPreferences(dto.isPreferences());

        customerReopository.save(customerProfile);

        return new UserDetailsResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getUsername(),
                savedUser.getRole(),
                savedUser.getEmail());
    }

    @Transactional
    public AuthResponseDto login(LoginRequestDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getIndentifier(), dto.getPassword()));

            User user = userRepository.findByEmailOrUsername(dto.getIndentifier(), dto.getIndentifier())
                    .orElseThrow(() -> new CustomException("Invalid credentials", HttpStatus.UNAUTHORIZED));

            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name(), user.getEmail());
            return new AuthResponseDto(token, new UserDetailsResponseDto(
                    user.getId(),
                    user.getName(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail()));
        } catch (Exception e) {
            throw new CustomException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
