package com.example.demo.model.dto;

import com.example.demo.model.enums.ConditionEnum;
import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.TransmissionEnum;

public class CarAdminDto {

    private Long id;

    private String carName;

    private String registrationPlate;

    private ConditionEnum condition;

    private Double pricePerDay;

    private EngineEnum engine;

    private TransmissionEnum transmission;

    private Integer capacity;


    private String fuelConsumption;

    public CarAdminDto(Long id, String carName, String registrationPlate, ConditionEnum condition,
                       Double pricePerDay, EngineEnum engine, TransmissionEnum transmission, Integer capacity, String fuelConsumption) {
        this.id = id;
        this.carName = carName;
        this.registrationPlate = registrationPlate;
        this.condition = condition;
        this.pricePerDay = pricePerDay;
        this.engine = engine;
        this.transmission = transmission;
        this.capacity = capacity;
        this.fuelConsumption = fuelConsumption;
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

    public ConditionEnum getCondition() {
        return condition;
    }

    public void setCondition(ConditionEnum condition) {
        this.condition = condition;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }
}
