package com.parcel_management_system.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Username or email is required")
    private String indentifier;

    @NotBlank(message = "Password is reqired")
    private String password;
}