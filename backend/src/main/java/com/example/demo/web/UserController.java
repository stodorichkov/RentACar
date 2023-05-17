package com.example.demo.web;


import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserProfileDto> userProfile(@PathVariable String username){
        return ResponseEntity.ok(this.userService.getUserProfileInfo(username));

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{username}/edit")
    public ResponseEntity<String> editProfile(
            @PathVariable String username,
            @RequestBody UserProfileDto userProfileDto
    ){
        String returnStatement = this.userService.editUserProfile(username,userProfileDto);
        return ResponseEntity.ok(returnStatement);
    }

}