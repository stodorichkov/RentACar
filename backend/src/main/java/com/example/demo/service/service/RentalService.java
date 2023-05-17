package com.example.demo.service.service;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.RentalDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {
    List<RentalEntity> getAllRentals();
    RentalEntity getRentalById(Long id);
    RentalEntity addRental(RentalDto rentalDto);
    RentalEntity updateRental(Long id, RentalDto rentalDto);
    void deleteRental(Long id);

    Double calculateRentalPrice(RentalEntity rental,double pricePerDay);

    long getRentalDays(LocalDateTime startDate, LocalDateTime endDate);

    long getOverdueDays(LocalDateTime currDay, LocalDateTime endDate);
}