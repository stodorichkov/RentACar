package com.example.demo.model;

import com.example.demo.model.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class StatusEntity extends Base{

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Double score;

    public StatusEntity(StatusEnum status, Double score) {
        this.status = status;
        this.score = score;
    }

    public StatusEntity(){}

    public  StatusEntity(StatusEnum status){
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
