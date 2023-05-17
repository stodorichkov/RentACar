package com.example.demo.model.dto;

import com.example.demo.model.enums.EngineEnum;
 import java.util.List;

public class CarEnumDto {

    private List<String> transmission;

    private List<String> engine;

    public CarEnumDto(List<String> transmission, List<String> engine) {
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
}
