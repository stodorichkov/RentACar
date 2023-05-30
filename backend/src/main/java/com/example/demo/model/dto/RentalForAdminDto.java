package com.example.demo.model.dto;

import com.example.demo.model.enums.StatusEnum;

public class RentalForAdminDto {
    private Long id;
    private String username;

    private String registrationPlate;

    private String carName;

    private StatusEnum status;

    private Double totalPrice;

    public RentalForAdminDto(Long id,String username, String registrationPlate, String carName, StatusEnum status, Double totalPrice) {
        this.id = id;
        this.username = username;
        this.registrationPlate = registrationPlate;
        this.carName = carName;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public  RentalForAdminDto(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
