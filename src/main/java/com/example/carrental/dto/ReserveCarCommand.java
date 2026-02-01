package com.example.carrental.dto;

import com.example.carrental.model.CarType;

import java.time.LocalDate;

public record ReserveCarCommand(
        CarType carType,
        LocalDate start,
        int length
) {
}
