package com.parcel_management_system.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceAlreadyExists extends CustomException {
    public ResourceAlreadyExists(String resourceName) {
        super(resourceName + " already exists.", HttpStatus.CONFLICT);
    }

    public ResourceAlreadyExists() {
        super("RESOURCE_ALREADY_EXISTS", HttpStatus.CONFLICT);
    }
}