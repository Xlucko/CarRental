package com.example.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PastReservationException extends ErrorResponseException {
    public PastReservationException() {
        super(HttpStatus.BAD_REQUEST);
        setDetail("Reservation can't start in past");
    }
}