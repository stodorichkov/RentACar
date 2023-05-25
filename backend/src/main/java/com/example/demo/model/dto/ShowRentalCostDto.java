package com.example.demo.model.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShowRentalCostDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long carId;

    public ShowRentalCostDto(LocalDateTime startTime, LocalDateTime endTime, Long carId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.carId = carId;
    }

    public ShowRentalCostDto(){}

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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

}
