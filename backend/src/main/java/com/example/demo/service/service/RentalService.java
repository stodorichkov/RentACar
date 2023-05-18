package com.example.demo.service.service;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.AddRentalDto;
import com.example.demo.model.dto.RentalCarDto;
import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.dto.UserProfileDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    List<RentalEntity> getAllRentals();
    RentalEntity getRentalById(Long id);
    String addRental(AddRentalDto addRentalDto, Principal principal);
    RentalEntity updateRental(Long id, RentalDto rentalDto);
    void deleteRental(Long id);
    Double calculateRentalPrice(RentalEntity rental,double pricePerDay);

    List<RentalCarDto> getUserRentalHistory(String username);

}