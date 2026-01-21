package com.parcel_management_system.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UnauthrizedException extends CustomException {
    public UnauthrizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthrizedException() {
        super("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }
}