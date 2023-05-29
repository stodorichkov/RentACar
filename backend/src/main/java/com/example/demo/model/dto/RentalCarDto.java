package com.example.demo.model.dto;

import com.example.demo.model.enums.StatusEnum;

import java.time.LocalDateTime;

public class RentalCarDto {


    private String carName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double price;

    private StatusEnum status;

    public RentalCarDto(){}

    public RentalCarDto(String carName, LocalDateTime startDate, LocalDateTime endDate, Double price, StatusEnum status) {
        this.carName = carName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
