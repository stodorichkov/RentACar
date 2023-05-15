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

    @ManyToMany
    private List<RoleEntity> roles;

    @OneToMany
    private List<RentalEntity> rentals;

    public UserEntity(String username, String email, String password,
                      Double budget, List<RoleEntity> roles, List<RentalEntity> rentals) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.budget = budget;
        this.roles = roles;
        this.rentals = rentals;
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
}
