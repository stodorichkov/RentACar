package com.example.demo.service.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;

import java.security.Principal;

import java.util.List;

public interface UserService {

    void seedAdmin();

    UserProfileDto getUserProfileInfo(String username);

    void deleteUser(Long id);

    String editUserProfile(String username,UserProfileDto userProfileDto);

    UserEntity findUserByName(String username);

    String addUser(UserRegisterDto userRegisterDto);

    void addMoneyToBudget(String username,MoneyDto moneyDto);

    UserEntity findById(Long id);

    List<UserProfileDto> findAllUsers();

    void setAsAdmin(Long id);



}
