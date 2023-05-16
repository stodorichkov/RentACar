package com.example.demo.model;

import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.TransmissionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity
public class CarEntity extends Base{

    private String make;

    private String model;

    private String registrationPlate;

    private String imageUrl;

    private Double pricePerHour;

    private Double pricePerDay;

    private Integer capacity;

    private TransmissionEnum transmission;

    private EngineEnum engine;

    private String fuelConsumption;

    private Boolean isRented;

    @ManyToMany
    private List<RentalEntity> carRental;



    public CarEntity(String make, String model, String registrationPlate, String imageUrl, Double pricePerHour,
                     Double pricePerDay, Integer capacity, TransmissionEnum transmission, EngineEnum engine,
                     String fuelConsumption, Boolean isRented, List<RentalEntity> carRental) {
        this.make = make;
        this.model = model;
        this.registrationPlate = registrationPlate;
        this.imageUrl = imageUrl;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.capacity = capacity;
        this.transmission = transmission;
        this.engine = engine;
        this.fuelConsumption = fuelConsumption;
        this.isRented = isRented;
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

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getRented() {
        return isRented;
    }

    public void setRented(Boolean rented) {
        isRented = rented;
    }

    public List<RentalEntity> getCarRental() {
        return carRental;
    }

    public void setCarRental(List<RentalEntity> carRental) {
        this.carRental = carRental;
    }
}
