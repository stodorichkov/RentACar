package com.example.demo.web;


import com.example.demo.model.dto.AuthenticatedUserDto;
import com.example.demo.model.dto.LoginUserDto;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.model.dto.UserRegisterDto;
import com.example.demo.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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

        if(!returnStatement.equals("Edit is successful")){
            return ResponseEntity.internalServerError().body(returnStatement);
        }

        return ResponseEntity.ok(returnStatement);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestBody UserRegisterDto userRegisterDto
    ){
        String returnStatement = this.userService.addUser(userRegisterDto);

        if(!returnStatement.equals("Registration was successful")){
            return ResponseEntity.internalServerError().body(returnStatement);
        }

        return ResponseEntity.ok(returnStatement);
    }


    @PostMapping("/login-error")
    public ResponseEntity<String> loginError(){
        return ResponseEntity.internalServerError().body("Wrong username or password.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authInfo(@RequestBody LoginUserDto loginUserDto){
//        AuthenticatedUserDto auth = new AuthenticatedUserDto();
//        if(this.userService.validateUser(loginUserDto)){
//            auth = this.userService.authUser(loginUserDto.getUsername());
//        }
//        if(auth == null){
//            return ResponseEntity.internalServerError().build();
//        }
        String response = this.userService.validateUser(loginUserDto);
        return  ResponseEntity.ok(response);
    }



}