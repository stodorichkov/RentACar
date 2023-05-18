package com.example.demo.service.implementation;

import com.example.demo.model.CarEntity;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;
import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.TransmissionEnum;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.CarService;
import com.example.demo.service.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import com.example.demo.exception.ObjectNotFoundException;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    private final RentalRepository rentalRepository;

    private final UserRepository userRepository;
    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper,
                          RentalRepository rentalRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.rentalRepository = rentalRepository;
        this.userRepository =  userRepository;
    }

    @Override
    public List<CarDto> getAllCars() {
       List<CarEntity> allCars = this.carRepository.findAll();
       List<CarDto> displayCars = new ArrayList<>();
       if(allCars!=null && !allCars.isEmpty()){
          displayCars = this.modelMapper.map(allCars,new TypeToken<List<CarDto>>(){}.getType());
       }
       return displayCars;
    }

    @Override
    public List<CarEntity> displayCarsByAvailability(Boolean result){
        if(displayCarsByAvailability(result).isEmpty()){
            throw new IllegalArgumentException("There are no available cars!");
        }
        return carRepository.findByIsRented(result);
    }



    //ToDo: maybe i need to delete this later
    @Override
    @Transactional
    public void deleteCar(Long id) {
        CarEntity carToDelete = this.getCarById(id);
//        List<RentalEntity> carRentals = carToDelete.getCarRental();
//        for(RentalEntity r : carRentals){
//            r.getRentedCars().remove(carToDelete);
//            this.rentalRepository.save(r);
//        }
        this.carRepository.deleteById(id);
    }



    @Override
    public CarDto getCarInfo(Long id) {
        CarEntity car = this.getCarById(id);
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

    @Override
    public CarEntity getCarById(Long id){
        return this.carRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Car with requested id:" + id + " was not found!")
        );
    }

    @Override
    @Transactional
    public String addCar(CarDto carDto, Principal principal) {

        CarEntity car = new CarEntity();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setRented(false);
        car.setImageUrl(carDto.getImageUrl());
        car.setPricePerDay(carDto.getPricePerDay());

        if(this.carRepository.findByRegistrationPlate(carDto.getRegistrationPlate()).isEmpty()){
            car.setRegistrationPlate(carDto.getRegistrationPlate());
        }else{
            return "Car with the same registration plate was already added!";
        }
        UserEntity admin = this.userRepository.findByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User with requested name:" + principal.getName() +
                        " was not found.")
        );
        admin.setAddedByAdmin(List.of(car));
        this.userRepository.save(admin);
        this.carRepository.save(car);
        return "Car is added successfully!";
    }

    @Override
    public CarEnumDto findCarEnumInfo() {

        CarEnumDto carEnumDto = new CarEnumDto();
        TransmissionEnum[] transmissionValues = TransmissionEnum.values();
        EngineEnum[] engineValues = EngineEnum.values();

        List<String> transmission = new ArrayList<>();
        for(TransmissionEnum t : transmissionValues){
            transmission.add(t.toString());
        }

        List<String> engine = new ArrayList<>();
        for(EngineEnum e : engineValues){
            engine.add(e.toString());
        }

        carEnumDto.setTransmission(transmission);
        carEnumDto.setEngine(engine);
        return carEnumDto;

    }

}