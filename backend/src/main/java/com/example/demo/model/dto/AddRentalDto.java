package com.example.demo.model.dto;

import java.time.LocalDateTime;

public class AddRentalDto {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public AddRentalDto(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AddRentalDto(){}

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
}
