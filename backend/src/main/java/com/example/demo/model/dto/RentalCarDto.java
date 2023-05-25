package com.example.demo.model.dto;

import java.time.LocalDateTime;

public class RentalCarDto {


    private String carMake;

    private String carModel;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double price;

    public RentalCarDto(){}

    public RentalCarDto(String carMake, String carModel, LocalDateTime startDate, LocalDateTime endDate, Double price) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
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
}
