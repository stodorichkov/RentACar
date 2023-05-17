package com.example.demo.initDb;

import com.example.demo.service.service.CarService;
import com.example.demo.service.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeed implements CommandLineRunner {

    private final RoleService roleService;

    private final CarService carService;

    public DatabaseSeed(RoleService roleService, CarService carService) {
        this.roleService = roleService;
        this.carService = carService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.roleService.checkRolesAndSeed();
        this.carService.addTestCar();
    }
}
