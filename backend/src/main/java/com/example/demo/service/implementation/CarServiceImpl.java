package com.example.demo.service.implementation;

import com.example.demo.model.CarEntity;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.CarAdminDto;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;
import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.RoleEnum;
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
import java.time.LocalDateTime;
import java.util.*;

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
    public Set<CarDto> getAllUniqueCars() {

       List<CarEntity> allCars = this.carRepository.findAll();
       List<CarDto> carToDisplay = this.modelMapper.map(allCars,new TypeToken<List<CarDto>>(){}.getType());

       Set<CarDto> filteredCars = new HashSet<>();

       filteredCars.addAll(carToDisplay);

       return filteredCars;
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
        this.carRepository.deleteById(id);
    }



    @Override
    public CarDto getCarInfo(Long id) {
        CarEntity car = this.getCarById(id);
        return this.modelMapper.map(car,CarDto.class);
    }

    @Override
    public void addTestCar() {

        if(this.carRepository.count() != 0){
            return;
        }

        CarEntity car = new CarEntity();
        car.setMake("Audi");
        car.setModel("A7");
        car.setPricePerDay(60.00);
        this.carRepository.save(car);

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

    @Override
    public CarEntity findCarById(Long id) {
        return this.carRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Car with requested id:" + id + " not found.")
        );
    }

    @Override
    public List<CarAdminDto> findCarsForAdmin(String username) {

        UserEntity admin = this.userRepository.findByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException("User with requested name:" + username + " was not found.")
        );
        List<CarAdminDto> carsToDisplay = new ArrayList<>();
        RoleEntity adminRole = new RoleEntity(RoleEnum.ADMIN);
        if(admin.getRoles().contains(adminRole)){
            List<CarEntity> adminCars = admin.getAddedByAdmin();
            for(CarEntity c : adminCars){
                carsToDisplay.add(
                        new CarAdminDto(
                                c.getId(),
                                c.getMake() + " " + c.getModel(),
                                c.getRegistrationPlate(),
                                c.getPricePerDay()
                        )
                );
            }

        }
        return carsToDisplay;
    }

    @Override
    public Set<CarDto> getUniqueAvailableCarsByDate(LocalDateTime startDate, LocalDateTime endDate) {

        List<CarEntity> cars = this.carRepository.findAllByTheirAvailability(startDate,endDate);
        List<CarDto> allCarsToDto = this.modelMapper.map(
                        cars, new TypeToken<List<CarDto>>(){}.getType()
                );
        Set<CarDto> uniqueCars = new HashSet<>();
        uniqueCars.addAll(allCarsToDto);
        return uniqueCars;
    }

}
