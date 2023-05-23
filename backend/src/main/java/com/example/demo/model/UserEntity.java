package com.example.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


import java.util.List;

@Entity
public class UserEntity extends Base{

    private String username;

    private String email;

    private String password;

    private Double budget;

    private Integer years;

    private String mobilePhone;

    private Double score;

    @ManyToMany
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "renter")
    private List<RentalEntity> rentals;

    @OneToMany
    private List<CarEntity> addedByAdmin;

    public UserEntity(String username, String email, String password,
                      Double budget, Integer years, String mobilePhone, Double score,
                      List<RoleEntity> roles, List<RentalEntity> rentals, List<CarEntity> addedByAdmin) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.budget = budget;
        this.years = years;
        this.mobilePhone = mobilePhone;
        this.score = score;
        this.roles = roles;
        this.rentals = rentals;
        this.addedByAdmin = addedByAdmin;
    }

    public UserEntity() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<RentalEntity> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalEntity> rentals) {
        this.rentals = rentals;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public List<CarEntity> getAddedByAdmin() {
        return addedByAdmin;
    }

    public void setAddedByAdmin(List<CarEntity> addedByAdmin) {
        this.addedByAdmin = addedByAdmin;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
