package com.example.carrental.repository;

import com.example.carrental.model.Car;
import com.example.carrental.model.CarType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CarRepository {

    private final List<Car> carList;

    public CarRepository() {
        carList = new ArrayList<>();
        carList.add(new Car(1L, "VAN_235", CarType.VAN));
        carList.add(new Car(2L, "VAN_278", CarType.VAN));
        carList.add(new Car(3L, "SUV_344", CarType.SUV));
        carList.add(new Car(4L, "SUV_235", CarType.SUV));
        carList.add(new Car(5L, "SED_861", CarType.SEDAN));
    }

    public Car getCarById(Long id) {
        return carList.stream()
                .filter(car -> Objects.equals(car.id(), id))
                .findFirst()
                .orElse(null);
    }

    public Car save(Car car) {
        Long largestId = carList.stream()
                .map(Car::id)
                .max(Long::compareTo)
                .orElse(0L);

        car.setId(largestId + 1);
        carList.add(car);
        return car;
    }

    public List<Car> getCarsByType(CarType type) {
        return carList.stream()
                .filter(car -> car.type() == type)
                .toList();
    }
}
