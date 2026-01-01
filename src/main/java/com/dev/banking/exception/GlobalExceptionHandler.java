package com.dev.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final WebRequest webRequest;

    public GlobalExceptionHandler(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    // Handle specific exception - Account Exception
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ErrorDetails> handleException(AccountException exception,
                                                        WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "ACCOUNT_NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    // Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception exception,
                                                               WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            exception.getMessage(),
            webRequest.getDescription(false),
            "INTERNAL_SERVER_ERROR"
    );
        return new ResponseEntity<> (errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
