package com.example.demo.service.service;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.*;
import org.springframework.cglib.core.Local;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    List<RentalEntity> getAllRentals();
    RentalEntity getRentalById(Long id);
    String addRental(AddRentalDto addRentalDto,Long carId, Principal principal);
    RentalEntity updateRental(Long id, RentalDto rentalDto);
    void deleteRental(Long id);
    double calculateRentalPrice(LocalDateTime startTime,LocalDateTime endTime,double pricePerDay);

    List<RentalCarDto> getUserRentalHistory(String username);

    void addTestRental();

    Double calculateMonthlyRevenue(int month, int year);

    Double showTotalCost( ShowRentalCostDto showRentalCostDto);

}

