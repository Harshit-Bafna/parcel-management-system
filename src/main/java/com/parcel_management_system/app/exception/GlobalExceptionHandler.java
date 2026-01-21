package com.parcel_management_system.app.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.parcel_management_system.app.dto.application.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getStatus().name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestError(BadRequestException e, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getStatus().name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExists e,
            HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getStatus().name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFound e, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getStatus().name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(UnauthrizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthrizedException e, HttpServletRequest req) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getStatus().value(),
                e.getStatus().name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e, HttpServletRequest req) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.name(),
                e.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}