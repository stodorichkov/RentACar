package com.example.demo.service.service;

import com.example.demo.model.CarEntity;
import java.util.List;

public interface CarService {
    List<CarEntity> getAllCars();
    List<CarEntity> displayCarsByAvailability(Boolean result);
}
