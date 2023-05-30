package com.example.demo;

import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.MoneyDto;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private UserServiceImpl userService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void itShouldGetUserProfileInfo() {

        Long id = 1L;
        String username = "john";
        String fullName = "John Smith";
        String email = "john@example.com";
        Double budget = 1000.0;
        Integer years = 3;
        Double score = 1.0;
        String mobilePhone = "1234567890";
        boolean isAdmin = false;

        UserProfileDto userProfileDto = new UserProfileDto(id,username,fullName,email,budget,years,score,mobilePhone,isAdmin);

        assertEquals(id, userProfileDto.getId());
        assertEquals(username, userProfileDto.getUsername());
        assertEquals(email, userProfileDto.getEmail());
        assertEquals(budget, userProfileDto.getBudget());
        assertEquals(years, userProfileDto.getYears());
        assertEquals(mobilePhone, userProfileDto.getMobilePhone());
    }
    @Test
    public void itShouldDeleteUser() {
        Long id = 1L;

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }
    @Test
    public void itShouldFindUserByName() {
        String username = "john";

        UserEntity user = new UserEntity();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserEntity result = userService.findUserByName(username);

        assertEquals(user, result);
    }

    @Test
    public void itShouldNotFindUserWhenNameDoesntExist() {
        String username = "john";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.findUserByName(username));
    }

    @Test
    public void itShouldAddMoneyToBudget() {
        String username = "john";
        double initialBudget = 100.0;
        double additionalMoney = 50.0;
        double expectedBudget = initialBudget + additionalMoney;

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setBudget(initialBudget);

        MoneyDto moneyDto = new MoneyDto();
        moneyDto.setMoney(additionalMoney);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.addMoneyToBudget(username, moneyDto);

        assertEquals(expectedBudget, user.getBudget());
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void findByIdTest() {
        Long userId = 1L;

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("john");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserEntity result = userService.findById(userId);

        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }
}
