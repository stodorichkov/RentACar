package com.example.demo.model.dto;

public class UserProfileDto {

    private Long id;

    private String username;

    private String fullName;
    private String email;
    private Double budget;
    private Integer years;

    private Double score;

    private String mobilePhone;

    private boolean isAdmin;


    public UserProfileDto(Long id, String username, String fullName, String email, Double budget,
                          Integer years, Double score, String mobilePhone, boolean isAdmin) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.budget = budget;
        this.years = years;
        this.score = score;
        this.mobilePhone = mobilePhone;
        this.isAdmin = isAdmin;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}