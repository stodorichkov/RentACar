package com.example.demo.service.implementation;


import com.example.demo.model.RoleEntity;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void checkRolesAndSeed() {

        long count = this.roleRepository.count();

        if(count != 0){
            return;
        }

        RoleEntity adminRole = new RoleEntity(RoleEnum.ADMIN);
        RoleEntity userRole = new RoleEntity(RoleEnum.USER);
        this.roleRepository.saveAll(List.of(adminRole,userRole));

    }
}
