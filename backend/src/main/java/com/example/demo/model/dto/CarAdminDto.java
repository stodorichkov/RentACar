package com.example.demo.model.dto;

public class CarAdminDto {

    private Long id;

    private String carName;

    private String registrationPlate;

    private Double pricePerDay;


    public CarAdminDto(Long id, String carName, String registrationPlate, Double pricePerDay) {
        this.id = id;
        this.carName = carName;
        this.registrationPlate = registrationPlate;
        this.pricePerDay = pricePerDay;
    }

    public CarAdminDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
