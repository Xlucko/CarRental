package com.example.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NoCarsAvailableException extends ErrorResponseException {
    public NoCarsAvailableException() {
        super(HttpStatus.CONFLICT);
        setDetail("No car available");
    }
}
