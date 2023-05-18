package com.example.demo.service.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.AuthenticatedUserDto;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.model.dto.UserRegisterDto;

import java.security.Principal;

public interface UserService {

    void seedAdmin();

    UserProfileDto getUserProfileInfo(String username);

    void deleteUser(Long id);

    String editUserProfile(String username,UserProfileDto userProfileDto);

    UserEntity findUserByName(String username);

    String addUser(UserRegisterDto userRegisterDto);

    UserEntity findById(Long id);

    boolean isAdmin(Principal principal);

    AuthenticatedUserDto authUser(Principal principal);

}
