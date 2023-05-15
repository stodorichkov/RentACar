package com.example.demo.model;

import com.example.demo.model.enums.RoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity
public class RoleEntity extends Base{

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEntity(RoleEnum role) {
        this.role = role;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
