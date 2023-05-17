package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.util.List;

@Entity
public class RentalEntity extends Base{

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime endTime;

    private Double totalPrice;

    @ManyToOne
    private CarEntity rentedCar;

    @ManyToOne
    private UserEntity renter;


    public RentalEntity(LocalDateTime startTime, LocalDateTime endTime, Double totalPrice,List<CarEntity> rentedCars) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.rentedCar = rentedCar;
        this.renter = renter;
    }

    public RentalEntity() {}


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


    public UserEntity getRenter() {
        return renter;
    }

    public CarEntity getRentedCar() {
        return rentedCar;
    }

    public void setRenter(UserEntity renter) {
        this.renter = renter;
    }

    public CarEntity getRenterCar() {
        return rentedCar;
    }

    public void setRentedCar(CarEntity rentedCar) {
        this.rentedCar = rentedCar;
    }
}
