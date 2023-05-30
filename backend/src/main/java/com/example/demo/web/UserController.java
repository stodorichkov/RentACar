package com.example.demo.web;


import com.example.demo.model.dto.LoginUserDto;
import com.example.demo.model.dto.MoneyDto;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.model.dto.UserRegisterDto;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.response.JwtResponse;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserController(UserService userService, ModelMapper modelMapper,
                          AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> userProfile(Authentication authentication){
        return ResponseEntity.ok(this.userService.getUserProfileInfo(authentication.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserProfileDto>> allUsersForAdmin(Authentication authentication){

       List<UserProfileDto> all =  this.userService.findAllUsers();
       if(all == null){
           return ResponseEntity.noContent().build();
       }
       return ResponseEntity.ok(all);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



    @PatchMapping("/edit")
    public ResponseEntity<String> editProfile(
            Authentication authentication,
            @RequestBody UserProfileDto userProfileDto
    ){

        String returnStatement = this.userService.editUserProfile(authentication.getName(), userProfileDto);

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


    @PutMapping("/add-money")
    public ResponseEntity<String> addMoney(
            Authentication authentication,
            @RequestBody MoneyDto moneyDto
                                           ){
        this.userService.addMoneyToBudget(authentication.getName(), moneyDto);
        return ResponseEntity.ok("Successfully added: " + moneyDto.getMoney() + " to your budget!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUserDto) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginUserDto.getUsername(),
                      loginUserDto.getPassword()
              )
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = jwtUtils.generateJwtToken(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> authorities = userDetails.getAuthorities().stream()
              .map(item -> item.getAuthority())
              .collect(Collectors.toList());

      return ResponseEntity.ok(
              new JwtResponse(jwt,
                      userDetails.getId(),
                      userDetails.getUsername(),
                      authorities)
      );
    }

    @PatchMapping("/{id}/set-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> setAsAdmin(@PathVariable Long id){
        this.userService.setAsAdmin(id);
        return ResponseEntity.ok().build();
    }




}