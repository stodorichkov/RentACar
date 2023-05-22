package com.example.demo.service.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;

import java.security.Principal;

public interface UserService {

    void seedAdmin();

    UserProfileDto getUserProfileInfo(String username);

    void deleteUser(String username);

    String editUserProfile(String username,UserProfileDto userProfileDto);

    UserEntity findUserByName(String username);

    String addUser(UserRegisterDto userRegisterDto);

    void addMoneyToBudget(String username,MoneyDto moneyDto);

    UserEntity findById(Long id);

    boolean isAdmin(Principal principal);



    String validateUser(LoginUserDto loginUserDto);

    void setAsAdmin(String username);

}
