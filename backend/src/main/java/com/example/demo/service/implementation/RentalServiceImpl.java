package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;
import com.example.demo.model.RentalEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.CarService;
import com.example.demo.service.service.RentalService;
import com.example.demo.service.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private  final UserRepository userRepository;

    private final UserService userService;

    private final CarService carService;
    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository, UserRepository userRepository, UserService userService, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
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
    public Double showTotalCost(ShowRentalCostDto showRentalCostDto) {
        CarEntity car = carRepository.findById(showRentalCostDto.getCarId())
                .orElseThrow(() -> new ObjectNotFoundException("Car not found"));
        double price = calculateRentalPrice(showRentalCostDto.getStartTime(), showRentalCostDto.getEndTime(), car.getPricePerDay());


        showRentalCostDto.setStartTime(showRentalCostDto.getStartTime());
        showRentalCostDto.setEndTime(showRentalCostDto.getEndTime());
        showRentalCostDto.setCarId(showRentalCostDto.getCarId());
        showRentalCostDto.setRentalCost(price);

        return price ;
    }
    @Override
    @Transactional
    public String addRental(AddRentalDto addRentalDto,Long carId) {

        RentalEntity rental = new RentalEntity();
        CarEntity currentCar = this.carService.findCarById(carId);
        currentCar.setRented(true);
        rental.setStartTime(addRentalDto.getStartTime());
        rental.setEndTime(addRentalDto.getEndTime());
        LocalDateTime currentTime = LocalDateTime.now();
        UserEntity renter = this.userService.findUserByName("Administrator");
        rental.setRenter(renter);
        rental.setRentedCar(currentCar);
        rental.setStatus("active");

        if (rental.getStartTime().isBefore(currentTime.plusHours(1))) {
            return "Cannot make a reservation 1 hour or less before your current time!";
        }

        double price = calculateRentalPrice(addRentalDto.getStartTime(), addRentalDto.getEndTime(), currentCar.getPricePerDay());

        // Check budget and existing reservations
        List<RentalEntity> userReservations = rentalRepository.findByRenterUserNameActive(renter.getUsername());
        double totalReservationCost = userReservations.stream().mapToDouble(RentalEntity::getTotalPrice).sum();
        double totalCost = totalReservationCost + price;

        double balance = renter.getBudget();
        if (balance > totalCost) {
            rental.setTotalPrice(price);
            this.rentalRepository.save(rental);
        } else {
            return "Not enough money for reservation!";
        }

        rental.setRenter(renter);
        rental.setTotalPrice(price);
        this.rentalRepository.save(rental);
        this.carRepository.save(currentCar);
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
    public String completeRental(CompleteRentalDto completeRentalDto) {
        RentalEntity rental = rentalRepository.findById(completeRentalDto.getRentalId())
                .orElseThrow(() -> new ObjectNotFoundException("Rental not found"));

        if (rental.getStatus().equals("completed")) {
            throw new RuntimeException("Rental is already completed");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = rental.getStartTime();
        LocalDateTime endTime = rental.getEndTime();
        UserEntity renter = rental.getRenter();
        CarEntity  car = rental.getRentedCar();
        double balance = renter.getBudget();
        double totalPrice = calculateRentalPrice(rental.getStartTime(),rental.getEndTime(), car.getPricePerDay());
        double payment = 0.0;


        long rentalDays = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());
        long currentRentalDays = ChronoUnit.DAYS.between(startTime.toLocalDate(), currentTime.toLocalDate());


        if (currentTime.isBefore(startTime.minusHours(1))) {
            rental.setStatus("canceled");
            rentalRepository.save(rental);
            return "Rental canceled ,charged: 0";
        }
        else if (currentRentalDays < rentalDays / 2) {
            payment = totalPrice / 2.0;
        } else {
            payment = totalPrice;
        }

       if (balance < payment) {
          return "Not enough money to complete the rental";
        } else {
            renter.setBudget(balance - payment);
            rental.setStatus("completed");

        }

        userRepository.save(renter);
        rentalRepository.save(rental);
        return "Rental complete!";
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
    public Double calculateMonthlyRevenue() {
        List<RentalEntity> rentals =
                this.rentalRepository.findAllFinishedRentalsForCurrentMonth(LocalDateTime.now().getMonthValue());

        //ToDo: check status of the rental
        double total = 0.0;
        for(RentalEntity r : rentals){
            total+=r.getTotalPrice();
        }

        return total;
    }



    @Override
    public void addTestRental() {

        if(this.rentalRepository.count()==0) {
            RentalEntity rental = new RentalEntity();
            rental.setStartTime(LocalDateTime.parse("2023-05-18T12:00:00"));
            rental.setEndTime(LocalDateTime.parse("2023-05-20T15:00:00"));
            rental.setTotalPrice(200.00);
            this.rentalRepository.save(rental);
        }

    }

}