package com.example.demo.model.dto;

import com.example.demo.model.enums.StatusEnum;

public class RentalCarDto {
    private Long id;
    private String carName;

    private String startDate;

    private String endDate;

    private Double price;

    private StatusEnum status;

    public RentalCarDto(){}

    public RentalCarDto(Long id, String carName, String startDate, String endDate, Double price, StatusEnum status) {
        this.id = id;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
