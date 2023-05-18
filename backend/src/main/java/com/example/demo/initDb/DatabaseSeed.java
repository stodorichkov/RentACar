package com.example.demo.initDb;

import com.example.demo.service.service.CarService;
import com.example.demo.service.service.RentalService;
import com.example.demo.service.service.RoleService;
import com.example.demo.service.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeed implements CommandLineRunner {

    private final RoleService roleService;

    private final UserService userService;

    private final CarService carService;

    private  final RentalService rentalService;
    public DatabaseSeed(RoleService roleService, UserService userService, CarService carService, RentalService rentalService) {
        this.roleService = roleService;
        this.userService = userService;
        this.carService = carService;
        this.rentalService = rentalService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.roleService.checkRolesAndSeed();
        this.userService.seedAdmin();
        this.carService.addTestCar();
        this.rentalService.addTestRental();
    }
}
