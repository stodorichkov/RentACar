package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsServiceImpl;
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

import java.security.Principal;
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

        if(this.userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername("Administrator");
            admin.setPassword(this.passwordEncoder.encode("Codexio10*"));
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }

    }

    @Override
    public UserProfileDto getUserProfileInfo(String username) {

        UserEntity currentUser = this.findUserByName(username);

        return this.modelMapper.map(currentUser,UserProfileDto.class);

    }

    @Override
    public void deleteUser(String username) {
        this.userRepository.deleteByUsername(username);
    }


    @Override
    public String editUserProfile(String username,UserProfileDto userProfileDto) {

        UserEntity editUser = this.findUserByName(username);

        if(editUser.getUsername()!=null && editUser.getUsername().equals(userProfileDto.getUsername())){
            return "Username already exists.";
        }else{
            editUser.setUsername(userProfileDto.getUsername());
        }

        if(editUser.getEmail()!=null && editUser.getEmail().equals(userProfileDto.getEmail())){
            return "Email already exists!";
        }else {
            editUser.setEmail(userProfileDto.getEmail());
        }

        if(editUser.getBudget()!=null && editUser.getBudget().equals(userProfileDto.getBudget())){
            return "Budget already exists!";
        }else {
            editUser.setBudget(userProfileDto.getBudget());
        }

        if(editUser.getYears()!=null &&  editUser.getYears().equals(userProfileDto.getYears())){
            return "Budget already exists!";
        }else {
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

        if(this.userRepository.findByUsername(userRegisterDto.getUsername()).isEmpty()){
            user.setUsername(userRegisterDto.getUsername());
        }else{
            return "Username already exist.";
        }

        if(this.userRepository.findByEmail(userRegisterDto.getEmail()).isEmpty() &&
                (userRegisterDto.getEmail()).matches("^[^\\s@]+@[^\\s@]+.[^\\s@]+$")){
            user.setEmail(userRegisterDto.getEmail());
        }else if(!(userRegisterDto.getEmail()).matches("^[^\\s@]+@[^\\s@]+.[^\\s@]+$")){
            return "Wrong email format.";
        } else if(!this.userRepository.findByEmail(userRegisterDto.getEmail()).isEmpty()){
            return "Email already exists.";
        }

        if(this.userRepository.findByMobilePhone(userRegisterDto.getMobilePhone()).isEmpty() &&
                (userRegisterDto.getMobilePhone()).matches("^\\d{10}$")){
            user.setMobilePhone(userRegisterDto.getMobilePhone());
        }else if (!(userRegisterDto.getMobilePhone()).matches("^\\d{10}$")){
            return "Wrong phone number";
        } else if(!this.userRepository.findByMobilePhone(userRegisterDto.getMobilePhone()).isEmpty()){
            return "Mobile phone already exists.";
        }

        if(userRegisterDto.getPassword().matches("^(?=.[A-Z])(?=.[a-z])(?=.\\d)(?=.[!@#$%^&])[A-Za-z\\d!@#$%^&].{7,}$") &&
                !userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            user.setPassword(this.passwordEncoder.encode(userRegisterDto.getPassword()));
        }else{
            return "Password is not valid! It must contain min 8 characters, one special and numbers";
        }

        RoleEntity userRole = this.roleRepository.findById(2l).orElseThrow(
                () -> new ObjectNotFoundException("Role with requested id:" + 2 + " was not found.")
        );
        user.setRoles(List.of(userRole));
        user.setYears(userRegisterDto.getYears());

        this.userRepository.save(user);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,user.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

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
    public boolean isAdmin(Principal principal) {
        return principal != null && principal instanceof Authentication &&
                ((Authentication) principal).getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

    }

    @Override
    public List<UserProfileDto> findAllUsers() {
        List<UserEntity> all = this.userRepository.findAll();
        if(all.isEmpty()){
            return null;
        }
        return this.modelMapper.map(all,new TypeToken<List<UserProfileDto>>(){}.getType());
    }


    @Override
    public void setAsAdmin(String username) {
       UserEntity user =  this.findUserByName(username);
       RoleEntity administrator = this.roleRepository.findById(1l).orElseThrow(
               () -> new ObjectNotFoundException("Role with requested id=" + 1 + " not fond,")
       );
       user.setRoles(List.of(administrator));
       this.userRepository.save(user);

    }


}