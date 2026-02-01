package com.example.carrental.dto;

import java.time.LocalDate;

public record CarReservationDTO(
        Long reservationId,
        LocalDate start,
        LocalDate end,
        String carPlate
) {
}
