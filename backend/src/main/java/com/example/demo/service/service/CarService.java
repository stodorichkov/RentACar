package com.example.demo.service.service;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarEntity> getAllCars();
    List<CarEntity> displayCarsByAvailability(Boolean result);

    void createCar(CarEntity car);

    void deleteCar(Long id);

    CarEntity findCarByMakeAndModel(String brand, String model);

    CarDto getCarInfo(String brand, String model);

    void addTestCar();
}
