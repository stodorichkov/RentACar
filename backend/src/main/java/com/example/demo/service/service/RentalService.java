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
    String addRental(AddRentalDto addRentalDto,Long carId);
    RentalEntity updateRental(Long id, RentalDto rentalDto);



    void deleteRental(Long id);
    double calculateRentalPrice(LocalDateTime startTime,LocalDateTime endTime,double pricePerDay);

    List<RentalCarDto> getUserRentalHistory(String username);

    Double calculateUserScore(String username);

    void addTestRental();

    Double  showTotalCost(ShowRentalCostDto showRentalCostDto);

    String completeRental(CompleteRentalDto completeRentalDto);
    Double calculateMonthlyRevenue(int month,int year);



}

