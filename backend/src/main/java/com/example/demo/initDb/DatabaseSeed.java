package com.example.demo.initDb;

import com.example.demo.service.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeed implements CommandLineRunner {

    private final RoleService roleService;

    private final UserService userService;

    private final StatusService statusService;

    public DatabaseSeed(RoleService roleService, UserService userService, StatusService statusService) {
        this.roleService = roleService;
        this.userService = userService;
        this.statusService = statusService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.roleService.checkRolesAndSeed();
        this.userService.seedAdmin();
        this.statusService.seedStatus();
    }
}
