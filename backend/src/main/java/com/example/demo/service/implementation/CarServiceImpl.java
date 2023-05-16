package com.example.demo.service.implementation;

import com.example.demo.model.CarEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.service.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarEntity> getAllCars() {
        if(carRepository.findAll().isEmpty()){
            throw new IllegalArgumentException("There are no cars at the moment!");
        }
        return carRepository.findAll();
    }

    @Override
    public List<CarEntity> displayCarsByAvailability(Boolean result){
        if(displayCarsByAvailability(result).isEmpty()){
            throw new IllegalArgumentException("There are no available cars!");
        }
        return carRepository.findCarByIsRented(result);
    }

}
