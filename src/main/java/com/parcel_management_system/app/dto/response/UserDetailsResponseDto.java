package com.parcel_management_system.app.dto.response;

import com.parcel_management_system.app.enums.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsResponseDto {
    private Long id;
    private String name;
    private String username;
    private ERole role;
    private String email;
}
