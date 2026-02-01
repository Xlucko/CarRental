package com.example.carrental.controller;

import com.example.carrental.dto.CarReservationDTO;
import com.example.carrental.dto.ReserveCarCommand;
import com.example.carrental.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public CarReservationDTO reserveCar(@RequestBody ReserveCarCommand command) {
        return reservationService.reserveCar(command);
    }
}
