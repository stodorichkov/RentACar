package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RentalDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalPrice;
    private List<CarDto> rentedCars;

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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CarDto> getRentedCars() {
        return rentedCars;
    }

    public void setRentedCars(List<CarDto> rentedCars) {
        this.rentedCars = rentedCars;
    }
}
