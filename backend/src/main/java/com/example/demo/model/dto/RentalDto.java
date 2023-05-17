package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RentalDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalPrice;
    private Long rentedCarId;
    private Long renterId;

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


    public Long getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(Long rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }
}
