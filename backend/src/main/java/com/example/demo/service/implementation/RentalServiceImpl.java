package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.AddRentalDto;
import com.example.demo.model.dto.RentalCarDto;
import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.dto.UserProfileDto;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.RentalService;
import com.example.demo.service.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    private final UserService userService;

    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.userService = userService;
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
    public String addRental(AddRentalDto addRentalDto, Principal principal) {

        RentalEntity rental = new RentalEntity();
        rental.setStartTime(addRentalDto.getStartTime());
        rental.setEndTime(addRentalDto.getEndTime());
        UserEntity renter = this.userService.findUserByName(principal.getName());
        rental.setRenter(renter);


        return "";
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

        UserEntity user = this.userService.findUserByName(rentalDto.getRenterUsername());
        rental.setRentedCar(car);
        rental.setRenter(user);

        return rentalRepository.save(rental);
    }

    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public Double calculateRentalPrice(RentalEntity rental, double pricePerDay) {
        LocalDateTime endTimeWithGracePeriod = rental.getEndTime().plusHours(2);
        LocalDateTime currentTime = LocalDateTime.now();

        long rentalDays = rental.getStartTime().until(endTimeWithGracePeriod, ChronoUnit.DAYS);
        if (rental.getStartTime().plusDays(rentalDays).isBefore(endTimeWithGracePeriod)) {
            rentalDays++;
        }

        long overdueDays = 0;
        if (currentTime.isAfter(endTimeWithGracePeriod)) {
            overdueDays = endTimeWithGracePeriod.until(currentTime, ChronoUnit.DAYS);
            if (endTimeWithGracePeriod.plusDays(overdueDays).isBefore(currentTime)) {
                overdueDays++;
            }
        }

        return (rentalDays + overdueDays) * pricePerDay;
    }

    @Override
    public List<RentalCarDto> getUserRentalHistory(String username) {

        UserEntity currentUser = this.userService.findUserByName(username);
        List<RentalEntity> currentRental = currentUser.getRentals();
        List<RentalCarDto> userRentalHistory = new ArrayList<>();
        for(RentalEntity r : currentRental){
            userRentalHistory.add(
                    new RentalCarDto(
                            r.getRentedCar().getMake(),
                            r.getRentedCar().getModel(),
                            r.getStartTime(),
                            r.getEndTime(),
                            r.getTotalPrice()
                    )
            );
        }
        return userRentalHistory;

    }

}