package com.example.demo.service.implementation;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
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

    @Override
    public void createCar(CarEntity car) {
        this.carRepository.save(car);
    }

    @Override
    public void deleteCar(Long id) {
        this.carRepository.deleteById(id);
    }

    @Override
    public CarEntity findCarByMakeAndModel(String brand,String model) {
        return this.carRepository.findByMakeAndModel(brand,model).
                orElseThrow(()-> new RuntimeException("There is no car with requested brand!"));
    }

    @Override
    public CarDto getCarInfo(String brand, String model) {
        if(carRepository.findByMakeAndModel(brand,model).isEmpty()){
            throw new IllegalArgumentException("There is no such car!");
        }
        CarEntity car = this.findCarByMakeAndModel(brand,model);
        return this.modelMapper.map(car,CarDto.class);
    }

    @Override
    public void addTestCar() {
        if(this.carRepository.count()==0){
            CarEntity car = new CarEntity();
            car.setMake("Audi");
            car.setModel("A7");
            car.setPricePerDay(60.00);
            this.carRepository.save(car);
        }
    }

}
