package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.util.List;

@Entity
public class RentalEntity extends Base{

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime endTime;

    @ManyToMany(mappedBy = "carRental")
    List<CarEntity> rentedCars;

    public RentalEntity(LocalDateTime startTime, LocalDateTime endTime, List<CarEntity> rentedCars) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.rentedCars = rentedCars;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<CarEntity> getRentedCars() {
        return rentedCars;
    }

    public void setRentedCars(List<CarEntity> rentedCars) {
        this.rentedCars = rentedCars;
    }
}
