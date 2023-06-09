package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void seedAdmin() {

        long count = this.userRepository.count();

        if(count != 0){
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setUsername("Administrator");
        admin.setPassword(this.passwordEncoder.encode("Codexio10*"));
        RoleEntity adminRole = this.roleRepository.findById(1l).get();
        RoleEntity userRole = this.roleRepository.findById(2l).get();
        admin.setRoles(List.of(adminRole,userRole));
        this.userRepository.save(admin);

    }

    @Override
    public UserProfileDto getUserProfileInfo(String username) {

        UserEntity currentUser = this.findUserByName(username);

        return this.modelMapper.map(currentUser,UserProfileDto.class);

    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }



    @Override
    public String editUserProfile(String username,UserProfileDto userProfileDto) {

        UserEntity editUser = this.findUserByName(username);

        if(editUser.getUsername().length()<=3){
            return "Username must contain at least 4 characters.";
        } else {
            editUser.setUsername(userProfileDto.getUsername());
        }

        if(!editUser.getFullName().matches("^[A-Z][a-z]{2,}\\s[A-Z][a-z]{2,}$")){
            return "Your first and family name is not correct.At least 3 characters for each name and space between!";
        } else {
            editUser.setFullName(userProfileDto.getFullName());
        }

       if(!editUser.getEmail().matches("^[^\\s@]+@[^\\s@]+.[^\\s@]+$")) {
            return "Email format is not correct.";
        } else {
            editUser.setEmail(userProfileDto.getEmail());
        }

        if(userProfileDto.getYears() < 18){
            return "You need to be at least 18 year old!";
        } else {
            editUser.setYears(userProfileDto.getYears());
        }

        this.userRepository.save(editUser);

        return "Edit is successful";
    }

    @Override
    public UserEntity findUserByName(String username) {

        return this.userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with requested name:" + username + " not found.")
                );
    }

    @Override
    @Transactional
    public String addUser(UserRegisterDto userRegisterDto) {

        UserEntity user = new UserEntity();
        user.setBudget(0.00);

        if(this.userRepository.findByUsername(userRegisterDto.getUsername()).isEmpty()
                && userRegisterDto.getUsername().length()>3){
            user.setUsername(userRegisterDto.getUsername());
        }else if (userRegisterDto.getUsername().length()<3){
            return "Username must contain at least 4 characters.";
        } else {
            return "Username already exist.";
        }

        if(this.userRepository.findByEmail(userRegisterDto.getEmail()).isEmpty() &&
                userRegisterDto.getEmail().matches("^[^\\s@]+@[^\\s@]+.[^\\s@]+$")){
            user.setEmail(userRegisterDto.getEmail());
        } else if (!(userRegisterDto.getEmail()).matches("^[^\\s@]+@[^\\s@]+.[^\\s@]+$")){
            return "Wrong email format.";
        } else if (!this.userRepository.findByEmail(userRegisterDto.getEmail()).isEmpty()){
            return "Email already exists.";
        }

        if(this.userRepository.findByMobilePhone(userRegisterDto.getMobilePhone()).isEmpty() &&
                (userRegisterDto.getMobilePhone()).matches("^\\d{10}$")){
            user.setMobilePhone(userRegisterDto.getMobilePhone());
        } else if (!(userRegisterDto.getMobilePhone()).matches("^\\d{10}$")){
            return "Wrong phone number";
        } else if (!this.userRepository.findByMobilePhone(userRegisterDto.getMobilePhone()).isEmpty()){
            return "Mobile phone already exists.";
        }

        if(userRegisterDto.getPassword().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$") &&
                userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            user.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));
        } else if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            return "Password and confirm password are not equal.";
        } else if (!userRegisterDto.getPassword().matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$")){
            return "Password is not valid! It must contain min 8 characters, one special and numbers";
        }

        if(userRegisterDto.getFullName().matches("^[A-Z][a-z]{2,}\\s[A-Z][a-z]{2,}$")){
            user.setFullName(userRegisterDto.getFullName());
        } else {
          return "Your first and family name is not correct.At least 3 characters for each name and space between!";
        }

        RoleEntity userRole = this.roleRepository.findById(2l).orElseThrow(
                () -> new ObjectNotFoundException("Role with requested id:" + 2 + " was not found.")
        );
        user.setRoles(List.of(userRole));
        user.setYears(userRegisterDto.getYears());

        user.setScore(0.0);

        this.userRepository.save(user);

        return "Registration was successful";
    }

    @Override
    public void addMoneyToBudget(String username, MoneyDto moneyDto) {
        UserEntity user = this.findUserByName(username);
        user.setBudget(user.getBudget() + moneyDto.getMoney());
        this.userRepository.save(user);
    }

    @Override
    public UserEntity findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("User with requested id:" + id + " not found")
        );
    }


    @Override
    public List<UserProfileDto> findAllUsers() {
        List<UserEntity> all = this.userRepository.findAll();
        if(all.isEmpty()){
            return null;
        }
        List<UserProfileDto> userInfo = new ArrayList<>();
        for(UserEntity u : all){
            userInfo.add(
                    new UserProfileDto(
                            u.getId(),
                            u.getUsername(),
                            u.getFullName(),
                            u.getEmail(),
                            u.getBudget(),
                            u.getYears(),
                            u.getScore(),
                            u.getMobilePhone(),
                            u.getRoles().size() > 1 ? true : false
                    )
            );
        }


        return userInfo;
    }


    @Override
    public void setAsAdmin(Long id) {
       UserEntity user =  this.findById(id);
       RoleEntity administrator = this.roleRepository.findById(1l).orElseThrow(
               () -> new ObjectNotFoundException("Role with requested id=" + 1 + " not fond,")
       );

       List<RoleEntity> updatedRoles = new ArrayList<>(user.getRoles());
       updatedRoles.add(administrator);
       user.setRoles(updatedRoles);
       this.userRepository.save(user);
    }



}