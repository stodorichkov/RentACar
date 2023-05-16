package com.example.demo.service.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.UserProfileDto;

public interface UserService {

    void seedAdmin();

    UserProfileDto getUserProfileInfo(String username);

    void deleteUser(Long id);

    String editUserProfile(String username,UserProfileDto userProfileDto);

    UserEntity findUserByName(String username);


}
