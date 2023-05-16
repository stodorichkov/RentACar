package com.example.demo.web;


import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user/{id}/profile")
    public ResponseEntity<UserProfileDto> userProfile(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.getUserProfileInfo(id));
    }
}
