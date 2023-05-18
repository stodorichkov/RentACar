package com.example.demo.service.service;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;

import java.security.Principal;
import java.util.List;

public interface CarService {
    List<CarDto> getAllCars();
    List<CarEntity> displayCarsByAvailability(Boolean result);

    void deleteCar(Long id);

    CarDto getCarInfo(Long id);

    void addTestCar();

    CarEntity getCarById(Long id);

    String addCar(CarDto carDto, Principal principal);

    CarEnumDto findCarEnumInfo();

    CarEntity findCarById(Long id);
}
