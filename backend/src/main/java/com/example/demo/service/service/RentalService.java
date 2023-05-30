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
    List<RentalCarDto> getUserRentalHistory(String username,boolean active);
    Double calculateUserScore(String username);

    Double  showTotalCost(String startDate,String endDate,Long carId);
    String completeRental(Long rentalId);
    Double calculateMonthlyRevenue(int month,int year);
    void changeStatus(Long id);
    RentalEntity findById(Long id);
    PaySummaryDto rentalCostSummary(Long rentalId);
    List<RentalForAdminDto> findRentalsInfoForAdmin();

}

