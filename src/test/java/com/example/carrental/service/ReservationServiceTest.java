package com.example.carrental.service;

import com.example.carrental.dto.CarReservationDTO;
import com.example.carrental.dto.ReserveCarCommand;
import com.example.carrental.exception.NoCarsAvailableException;
import com.example.carrental.exception.PastReservationException;
import com.example.carrental.model.Car;
import com.example.carrental.model.CarReservation;
import com.example.carrental.model.CarType;
import com.example.carrental.repository.CarRepository;
import com.example.carrental.repository.CarReservationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @MockitoBean
    CarRepository carRepository;

    @MockitoBean
    CarReservationRepository carReservationRepository;

    @MockitoSpyBean
    ReservationService reservationService;

    @Test
    public void givenNoReservations_WhenReserveCar_CreateAndReturnReservation() {
        Car car = new Car(101L, "TEST101", CarType.VAN);

        Mockito.when(carRepository.getCarsByType(CarType.VAN)).thenReturn(List.of(car));
        Mockito.when(carReservationRepository.getForCar(car)).thenReturn(Collections.emptyList());
        Mockito.when(carReservationRepository.save(Mockito.any(CarReservation.class))).thenAnswer(i -> {
                    CarReservation carReservation = (CarReservation) i.getArguments()[0];
                    carReservation.setId(201L);
                    return carReservation;
                }
        );

        ReserveCarCommand command = new ReserveCarCommand(CarType.VAN, LocalDate.now().plusDays(5), 5);
        CarReservationDTO reservation = reservationService.reserveCar(command);

        assertNotNull(reservation);
        assertEquals(car.licencePlate(), reservation.carPlate());
        Mockito.verify(carReservationRepository).save(Mockito.any());

    }

    @Test
    public void givenNonOverlappingReservations_WhenReserveCar_CreateAndReturnReservation() {
        LocalDate now = LocalDate.now();
        Car car = new Car(101L, "TEST101", CarType.VAN);
        Mockito.when(carRepository.getCarsByType(CarType.VAN)).thenReturn(List.of(car));
        Mockito.when(carReservationRepository.getForCar(car)).thenReturn(
                List.of(
                        new CarReservation(201L, car, now.minusDays(10), 5),
                        new CarReservation(201L, car, now.plusDays(20), 3)
                )
        );
        Mockito.when(carReservationRepository.save(Mockito.any(CarReservation.class))).thenAnswer(i -> {
                    CarReservation carReservation = (CarReservation) i.getArguments()[0];
                    carReservation.setId(201L);
                    return carReservation;
                }
        );

        ReserveCarCommand command = new ReserveCarCommand(CarType.VAN, now.plusDays(10), 6);
        CarReservationDTO reservation = reservationService.reserveCar(command);

        assertNotNull(reservation);
        assertEquals(car.licencePlate(), reservation.carPlate());
        Mockito.verify(carReservationRepository).save(Mockito.any());
    }

    @Test
    public void givenNoCars_WhenReserveCar_ThrowNoCarsAvailableException() {
        LocalDate now = LocalDate.now();
        Mockito.when(carRepository.getCarsByType(CarType.VAN)).thenReturn(List.of());
        Mockito.when(carReservationRepository.getForCar(Mockito.argThat(car -> car.type().equals(CarType.VAN))))
                .thenReturn(List.of());

        ReserveCarCommand command = new ReserveCarCommand(CarType.VAN, now.plusDays(10), 6);
        assertThrows(NoCarsAvailableException.class, () -> reservationService.reserveCar(command));
    }

    @Test
    public void givenNoAvailableCars_WhenReserveCar_ThrowNoCarsAvailableException() {
        //given
        LocalDate now = LocalDate.now();
        Car van1 = new Car(101L, "TEST_101", CarType.VAN);
        Car van2 = new Car(102L, "TEST_102", CarType.VAN);
        Car van3 = new Car(103L, "TEST_103", CarType.VAN);
        Mockito.when(carRepository.getCarsByType(CarType.VAN)).thenReturn(List.of(
                van1, van2, van3
        ));
        Mockito.when(carReservationRepository.getForCar(van1))
                .thenReturn(List.of(new CarReservation(201L, van1, now.plusDays(8), 4)));
        Mockito.when(carReservationRepository.getForCar(van2))
                .thenReturn(List.of(new CarReservation(202L, van2, now.plusDays(11), 3)));
        Mockito.when(carReservationRepository.getForCar(van3))
                .thenReturn(List.of(new CarReservation(203L, van3, now.plusDays(12), 7)));

        //then
        ReserveCarCommand command = new ReserveCarCommand(CarType.VAN, now.plusDays(10), 5);
        assertThrows(NoCarsAvailableException.class, () -> reservationService.reserveCar(command));
    }

    @Test
    public void givenReservationWithPastDate_WhenReserveCar_ThrowPastReservationException() {
        LocalDate now = LocalDate.now();
        Mockito.when(carRepository.getCarsByType(CarType.VAN)).thenReturn(List.of());
        Mockito.when(carReservationRepository.getForCar(Mockito.argThat(car -> car.type().equals(CarType.VAN))))
                .thenReturn(List.of());

        ReserveCarCommand command = new ReserveCarCommand(CarType.VAN, now.minusDays(2), 6);
        assertThrows(PastReservationException.class, () -> reservationService.reserveCar(command));
    }
}