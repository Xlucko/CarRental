package com.example.carrental.repository;


import com.example.carrental.model.Car;
import com.example.carrental.model.CarReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CarReservationRepository {

    private final List<CarReservation> carReservationList;

    public CarReservationRepository() {
        carReservationList = new ArrayList<>();
    }

    public CarReservation getById(Long id) {
        if (id == null) {
            return null;
        }
        return carReservationList.stream()
                .filter(carReservation -> Objects.equals(carReservation.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public CarReservation save(CarReservation carReservation) {
        Long largestId = carReservationList.stream()
                .map(CarReservation::getId)
                .max(Long::compareTo)
                .orElse(0L);
        carReservation.setId(largestId + 1);
        carReservationList.add(carReservation);
        return carReservation;
    }

    public List<CarReservation> getForCar(Car car) {
        return carReservationList.stream()
                .filter(carReservation -> carReservation.getCar() == car)
                .toList();
    }
}