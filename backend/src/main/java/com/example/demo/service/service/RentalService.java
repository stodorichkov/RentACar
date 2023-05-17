package com.example.demo.service.service;

import com.example.demo.model.CarEntity;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.RentalDto;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface RentalService {
    List<RentalEntity> getAllRentals();
    RentalEntity getRentalById(Long id);
    RentalEntity addRental(RentalDto rentalDto);
    RentalEntity updateRental(Long id, RentalDto rentalDto);
    void deleteRental(Long id);

    Double calculateRentalPrice(double pricePerDay, LocalDateTime startDate, LocalDateTime endDate);
}