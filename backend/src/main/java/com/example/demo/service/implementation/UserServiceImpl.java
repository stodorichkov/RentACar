package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.AuthenticatedUserDto;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.model.dto.UserRegisterDto;
import com.example.demo.model.enums.RoleEnum;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
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

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedAdmin() {

        if(this.userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setUsername("Administrator");
            admin.setPassword(this.passwordEncoder.encode("Codexio10*"));
            this.userRepository.save(admin);
        }

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
            return "Username already existed.";
        }

        if(this.userRepository.findByEmail(userRegisterDto.getEmail()).isEmpty()){
            user.setUsername(userRegisterDto.getEmail());
        }else{
            return "Email already exists.";
        }
        if (!(userRegisterDto.getEmail()).matches("^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")){
            return "Wrong format";
        }
        else {
            user.setEmail(userRegisterDto.getEmail());
        }

        if(this.userRepository.findByMobilePhone(userRegisterDto.getMobilePhone()).isEmpty()){
            user.setMobilePhone(userRegisterDto.getMobilePhone());
        }else if (!(userRegisterDto.getMobilePhone()).matches("(^$|[0-9]{10})")){
            return "Wrong format";
        }
        else{
            return "Mobile phone already exists.";
        }


        RoleEntity userRole = this.roleRepository.findById(2l).orElseThrow(
                () -> new ObjectNotFoundException("Role with requested id:" + 2 + " was not found.")
        );
        user.setRoles(List.of(userRole));
        user.setPassword(userRegisterDto.getPassword());
        user.setYears(userRegisterDto.getYears());

        this.userRepository.save(user);

        return "Registration was successful";

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
    public AuthenticatedUserDto authUser(Principal principal) {
        AuthenticatedUserDto auth = new AuthenticatedUserDto();
        UserEntity authUser = this.findUserByName(principal.getName());
        auth.setUsername(authUser.getUsername());
        auth.setId(authUser.getId());
        auth.setYears(authUser.getYears());
        auth.setEmail(authUser.getEmail());
        auth.setAdmin(this.isAdmin(principal));
        return auth;
    }


}