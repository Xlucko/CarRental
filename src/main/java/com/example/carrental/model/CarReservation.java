package com.example.carrental.model;

import java.time.LocalDate;
import java.util.Objects;

public final class CarReservation {
    private Long id;
    private final Car car;
    private final LocalDate start;
    private final LocalDate end;

    public CarReservation(
            Long id,
            Car car,
            LocalDate start,
            Integer days
    ) {
        this.id = id;
        this.car = car;
        this.start = start;
        this.end = start.plusDays(days);
    }


    public CarReservation(
            Car car,
            LocalDate start,
            Integer days
    ) {
        this.car = car;
        this.start = start;
        this.end = start.plusDays(days);
    }

    public Long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CarReservation) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.car, that.car) &&
                Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, car, start, end);
    }

    @Override
    public String toString() {
        return "CarReservation[" +
                "id=" + id + ", " +
                "car=" + car + ", " +
                "start=" + start + ", " +
                "edn=" + end + ']';
    }

}
