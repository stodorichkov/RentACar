package com.example.demo.model.dto;

public class AuthenticatedUserDto {

    private Long id;

    private String username;

    private String mobilePhone;

    private Integer years;

    private String email;

    private boolean isAdmin;

    public AuthenticatedUserDto(){}

    public AuthenticatedUserDto(Long id, String username, String mobilePhone, Integer years, String email, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.mobilePhone = mobilePhone;
        this.years = years;
        this.email = email;
        this.isAdmin = isAdmin;
    }

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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
