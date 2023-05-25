package com.example.demo.model.dto;

import java.util.List;

public class CarEnumDto {

    private List<String> transmission;

    private List<String> engine;

    private List<String> condition;

    public CarEnumDto(List<String> transmission, List<String> engine, List<String> condition) {
        this.condition = condition;
        ;
        this.transmission = transmission;
        this.engine = engine;
    }

    public CarEnumDto(){}

    public List<String> getTransmission() {
        return transmission;
    }

    public void setTransmission(List<String> transmission) {
        this.transmission = transmission;
    }

    public List<String> getEngine() {
        return engine;
    }

    public void setEngine(List<String> engine) {
        this.engine = engine;
    }

    public List<String> getCondition() {
        return condition;
    }

    public void setCondition(List<String> condition) {
        this.condition = condition;
    }
}
