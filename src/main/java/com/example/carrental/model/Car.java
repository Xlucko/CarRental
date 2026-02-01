package com.example.carrental.model;

import java.util.Objects;

public final class Car {
    private Long id;
    private final String licencePlate;
    private final CarType type;

    public Car(
            Long id,
            String licencePlate,
            CarType type
    ) {
        this.id = id;
        this.licencePlate = licencePlate;
        this.type = type;
    }

    public Car(
            String licencePlate,
            CarType type
    ) {
        this.licencePlate = licencePlate;
        this.type = type;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String licencePlate() {
        return licencePlate;
    }

    public CarType type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Car) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.licencePlate, that.licencePlate) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, licencePlate, type);
    }

    @Override
    public String toString() {
        return "Car[" +
                "id=" + id + ", " +
                "licencePlate=" + licencePlate + ", " +
                "type=" + type + ']';
    }

}
