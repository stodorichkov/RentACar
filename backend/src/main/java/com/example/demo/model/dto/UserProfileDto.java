package com.example.demo.model.dto;

import jakarta.validation.constraints.*;

public class UserProfileDto {

    private Long id;


    private String username;

    private String email;

    private Double budget;

    private Integer years;

    private String mobilePhone;


    public UserProfileDto(Long id, String username, String email, Double budget, Integer years, String mobilePhone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.budget = budget;
        this.years = years;
        this.mobilePhone = mobilePhone;
    }

    public UserProfileDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}