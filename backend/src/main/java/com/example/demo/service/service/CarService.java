package com.example.demo.service.service;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarAdminDto;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;
import com.example.demo.model.dto.EditConditionAndPriceDto;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

public interface CarService {
    Set<CarDto> getAllUniqueCars();
    void deleteCar(Long id);

    CarDto getCarInfo(Long id);

    void addTestCar();

    CarEntity getCarById(Long id);

    String addCar(CarDto carDto, String username);

    CarEnumDto findCarEnumInfo();

    List<CarAdminDto> findCarsForAdmin(String username);


    Set<CarDto> getUniqueAvailableCarsByDate(String startDate, String endDate);

    String editConditionAndPrice(EditConditionAndPriceDto editConditionAndPriceDto, Long carId);
}
