package com.example.demo.service.service;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.*;
import org.springframework.cglib.core.Local;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface RentalService {
    List<RentalEntity> getAllRentals();
    RentalEntity getRentalById(Long id);
    String addRental(String username,AddRentalDto addRentalDto,Long carId);

    double calculateRentalPrice(LocalDateTime startTime,LocalDateTime endTime,double pricePerDay);
    List<RentalCarDto> getUserRentalHistory(String username);
    Double calculateUserScore(String username);
    void addTestRental();
    Double  showTotalCost(ShowRentalCostDto showRentalCostDto);
    String completeRental(Long rentalId);
    Double calculateMonthlyRevenue(int month,int year);
    void changeStatus(Long id);
    RentalEntity findById(Long id);
    HashMap<String,Double> rentalCostSummary(Long rentalId);

    List<RentalForAdminDto> findRentalsInfoForAdmin();

}

