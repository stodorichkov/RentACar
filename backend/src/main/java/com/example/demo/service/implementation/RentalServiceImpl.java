package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.AddRentalDto;
import com.example.demo.model.dto.RentalCarDto;
import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.RentalEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.service.service.CarService;
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

    private final CarService carService;
    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository, UserService userService, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.userService = userService;
        this.carService = carService;
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
    public String addRental(AddRentalDto addRentalDto,Long carId, Principal principal) {

        RentalEntity rental = new RentalEntity();
        CarEntity currentCar = this.carService.findCarById(carId);
        rental.setStartTime(addRentalDto.getStartTime());
        rental.setEndTime(addRentalDto.getEndTime());
        UserEntity renter = this.userService.findUserByName(principal.getName());
        rental.setRenter(renter);
        rental.setTotalPrice(calculateRentalPrice(
                addRentalDto.getStartTime(),
                addRentalDto.getEndTime(),
                currentCar.getPricePerDay()
                ));

        return "Everything was successful.";
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
    public double calculateRentalPrice(LocalDateTime startTime,LocalDateTime endTime, double pricePerDay) {
        LocalDateTime endTimeWithGracePeriod = endTime.plusHours(2);
        LocalDateTime currentTime = LocalDateTime.now();

        long rentalDays = startTime.until(endTimeWithGracePeriod, ChronoUnit.DAYS);
        if (startTime.plusDays(rentalDays).isBefore(endTimeWithGracePeriod)) {
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

    @Override
    public Double calculateMonthlyRevenue(int month, int year) {
        List<RentalEntity> rentals = rentalRepository.findAll();

        double total = rentals.stream()
                .filter(rental -> rental.getStartTime().getMonthValue() == month && rental.getStartTime().getYear() == year)
                .mapToDouble(RentalEntity::getTotalPrice)
                .sum();

        return total;
    }



    @Override
    public void addTestRental() {

            RentalEntity rental = new RentalEntity();
            rental.setStartTime(LocalDateTime.parse("2023-05-18T12:00:00"));
            rental.setEndTime(LocalDateTime.parse("2023-05-20T15:00:00"));
            rental.setTotalPrice(200.00);
            this.rentalRepository.save(rental);

    }

}