package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public UserProfileDto getUserProfileInfo(Long id) {

        UserEntity currentUser = this.userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("User with requested id:" + id + " not found.")
        );

        return this.modelMapper.map(currentUser,UserProfileDto.class);

    }
}
