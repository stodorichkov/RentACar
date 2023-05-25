package com.example.demo.model.dto;


public class UserRegisterDto {

    private Long id;

    private String username;

    private String fullName;
    private String email;

    private Integer years;

    private String mobilePhone;

    private String password;

    private String confirmPassword;

    public UserRegisterDto(Long id, String username, String fullName, String email, Integer years, String mobilePhone, String password, String confirmPassword) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.years = years;
        this.mobilePhone = mobilePhone;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
