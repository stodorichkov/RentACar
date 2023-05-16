package com.example.demo.service.service;

import com.example.demo.model.dto.UserProfileDto;

public interface UserService {

    void seedAdmin();

    UserProfileDto getUserProfileInfo(Long id);
}
