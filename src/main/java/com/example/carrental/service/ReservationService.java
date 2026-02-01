package com.example.carrental.service;

import com.example.carrental.dto.CarReservationDTO;
import com.example.carrental.dto.ReserveCarCommand;
import com.example.carrental.exception.NoCarsAvailableException;
import com.example.carrental.exception.PastReservationException;
import com.example.carrental.model.Car;
import com.example.carrental.model.CarReservation;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.CarReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final CarRepository carRepository;
    private final CarReservationRepository carReservationRepository;

    public ReservationService(CarRepository carRepository, CarReservationRepository carReservationRepository) {
        this.carRepository = carRepository;
        this.carReservationRepository = carReservationRepository;
    }

    public CarReservationDTO reserveCar(ReserveCarCommand command) {
        if (command.start().isBefore(LocalDate.now())) {
            throw new PastReservationException();
        }

        Car car = carRepository.getCarsByType(command.carType()).stream()
                .filter(c -> isAvailable(c, command.start(), command.length()))
                .findFirst()
                .orElseThrow(NoCarsAvailableException::new);

        CarReservation carReservation = new CarReservation(car, command.start(), command.length());
        carReservation = carReservationRepository.save(carReservation);
        return new CarReservationDTO(carReservation.getId(), carReservation.getStart(), carReservation.getEnd(), carReservation.getCar().licencePlate());
    }

    private boolean isAvailable(Car car, LocalDate start, int days) {
        List<CarReservation> carReservationList = carReservationRepository.getForCar(car);
        LocalDate end = start.plusDays(days);
        carReservationList = carReservationList.stream()
                .filter(carReservation -> carReservation.getEnd().isAfter(start))
                .filter(carReservation -> carReservation.getStart().isBefore(end))
                .toList();
        return CollectionUtils.isEmpty(carReservationList);
    }
}
