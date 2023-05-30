package com.example.demo.model.dto;

import com.example.demo.model.enums.ConditionEnum;


public class EditConditionAndPriceDto {

    private Long carId;

    private Double pricePerDay;

    private ConditionEnum condition;


    public EditConditionAndPriceDto(Long carId, Double pricePerDay, ConditionEnum condition) {
        this.carId = carId;
        this.pricePerDay = pricePerDay;
        this.condition = condition;
    }

    public EditConditionAndPriceDto(){}

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public void setConditionEnum(ConditionEnum condition) {
        this.condition = condition;
    }
}
