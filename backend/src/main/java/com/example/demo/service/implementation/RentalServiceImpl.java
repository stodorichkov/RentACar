package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.RentalEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.RentalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    private final UserRepository userRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<RentalEntity> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public RentalEntity getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Rental not found"));
    }


    @Override
    public RentalEntity addRental(RentalDto rentalDto) {
        RentalEntity rental = new RentalEntity();
        rental.setStartTime(rentalDto.getStartTime());
        rental.setEndTime(rentalDto.getEndTime());
        rental.setTotalPrice(rentalDto.getTotalPrice());

        CarEntity car = carRepository.findById(rentalDto.getRentedCarId())
                .orElseThrow(() -> new ObjectNotFoundException("Car not found"));

        UserEntity user = userRepository.findById(rentalDto.getRenterId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        rental.setRentedCar(car);
        rental.setRenter(user);

        return rentalRepository.save(rental);
    }

    @Override
    public RentalEntity updateRental(Long id, RentalDto rentalDto) {
        RentalEntity rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Rental not found"));

        rental.setStartTime(rentalDto.getStartTime());
        rental.setEndTime(rentalDto.getEndTime());
        rental.setTotalPrice(rentalDto.getTotalPrice());

        CarEntity car = carRepository.findById(rentalDto.getRentedCarId())
                .orElseThrow(() -> new ObjectNotFoundException("Car not found"));

        UserEntity user = userRepository.findById(rentalDto.getRenterId())
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        rental.setRentedCar(car);
        rental.setRenter(user);

        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public Double calculateRentalPrice(double pricePerDay, LocalDateTime startDate, LocalDateTime endDate) {

        double rentalDays = getRentalDays(startDate,endDate);
        LocalDateTime currentDate = LocalDateTime.now();
        double totalPrice = pricePerDay * rentalDays;
        if(currentDate.isAfter(endDate)){
            totalPrice += pricePerDay;
        }
        return totalPrice;
    }
    private long getRentalDays(LocalDateTime startDate, LocalDateTime endDate) {
        long diff = endDate.getDayOfYear() - startDate.getDayOfYear();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}

