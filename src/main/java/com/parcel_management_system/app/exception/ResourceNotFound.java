package com.parcel_management_system.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceNotFound extends CustomException {
    public ResourceNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFound() {
        super("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}