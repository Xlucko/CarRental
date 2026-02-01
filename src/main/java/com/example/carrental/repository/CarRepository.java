package com.example.carrental.repository;

import com.example.carrental.model.Car;
import com.example.carrental.model.CarType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CarRepository {

    private final List<Car> cars;

    public CarRepository() {
        cars = new ArrayList<>();
    }

    public Car getCarById(Long id) {
        return cars.stream()
                .filter(car -> Objects.equals(car.id(), id))
                .findFirst()
                .orElse(null);
    }

    public Car save(Car car) {
        Long largestId = cars.stream()
                .map(Car::id)
                .max(Long::compareTo)
                .orElse(0L);

        car.setId(largestId + 1);
        cars.add(car);
        return car;
    }

    public List<Car> getCarsByType(CarType type) {
        return cars.stream()
                .filter(car -> car.type() == type)
                .toList();
    }
}
