package com.example.demo.model.dto;

import jakarta.validation.constraints.*;


public class UserRegisterDto {

    private Long id;


    @NotEmpty(message = "Username must not be empty")

    private String username;



    private String email;

    private Integer years;

    private String mobilePhone;



    private String password;

    public UserRegisterDto(Long id, String username, String email, Integer years, String mobilePhone, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.years = years;
        this.mobilePhone = mobilePhone;
        this.password = password;
    }

    public UserRegisterDto(){}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
