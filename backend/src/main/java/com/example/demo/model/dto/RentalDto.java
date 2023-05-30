package com.example.demo.model.dto;

import java.time.LocalDateTime;

public class RentalDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalPrice;
    private Long rentedCarId;
    private Long renterId;
    private String renterUsername;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRenterUsername() {
        return renterUsername;
    }

    public void setRenterUsername(String renterUsername) {
        this.renterUsername = renterUsername;
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
