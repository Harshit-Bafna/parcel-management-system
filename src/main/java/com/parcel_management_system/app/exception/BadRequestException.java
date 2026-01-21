package com.parcel_management_system.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException() {
        super("BAD_REQUEST", HttpStatus.BAD_REQUEST);
    }
}