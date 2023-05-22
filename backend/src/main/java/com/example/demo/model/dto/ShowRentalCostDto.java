package com.example.demo.model.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShowRentalCostDto {
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

    public Double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(Double totalPrice) {
        this.rentalCost = totalPrice;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double rentalCost;
    private Long carId;

}
