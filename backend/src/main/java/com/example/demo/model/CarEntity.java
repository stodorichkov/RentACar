package com.example.demo.model;

import com.example.demo.model.enums.ConditionEnum;
import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.TransmissionEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class CarEntity extends Base{

    private String make;

    private String model;

    private String registrationPlate;

    @Lob
    private String imageUrl;

    private Double pricePerDay;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private TransmissionEnum transmission;

    @Enumerated(EnumType.STRING)
    private EngineEnum engine;

    private String fuelConsumption;

    @Enumerated(EnumType.STRING)
    private ConditionEnum carCondition;


    @OneToMany(mappedBy = "rentedCar",cascade = CascadeType.ALL)
    private List<RentalEntity> carRental;

    public CarEntity(String make, String model, String registrationPlate, String imageUrl, Double pricePerHour,
                     Double pricePerDay, Integer capacity, TransmissionEnum transmission, EngineEnum engine,
                     String fuelConsumption, ConditionEnum carCondition, List<RentalEntity> carRental) {

        this.make = make;
        this.model = model;
        this.registrationPlate = registrationPlate;
        this.imageUrl = imageUrl;
        this.pricePerDay = pricePerDay;
        this.capacity = capacity;
        this.transmission = transmission;
        this.engine = engine;
        this.fuelConsumption = fuelConsumption;
        this.carCondition = carCondition;
        this.carRental = carRental;
    }

    public CarEntity() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<RentalEntity> getCarRental() {
        return carRental;
    }

    public void setCarRental(List<RentalEntity> carRental) {
        this.carRental = carRental;
    }

    public ConditionEnum getCondition() {
        return carCondition;
    }

    public void setCondition(ConditionEnum carCondition) {
        this.carCondition = carCondition;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }
}
