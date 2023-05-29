package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.StatusEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.*;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.enums.StatusEnum;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.service.CarService;
import com.example.demo.service.service.RentalService;
import com.example.demo.service.service.StatusService;
import com.example.demo.service.service.UserService;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private  final UserRepository userRepository;

    private final StatusService statusService;
    private final UserService userService;

    private final CarService carService;
    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository, UserRepository userRepository,
                             StatusService statusService, UserService userService, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.statusService = statusService;

        this.userService = userService;
        this.carService = carService;

    }
    @Override
    public List<RentalEntity> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public RentalEntity getRentalById(Long id) {
        return rentalRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Rental with id:" + id + " was not found!"));
    }



    @Override
    public Double showTotalCost(String startDate,String endDate,Long carId) {

        CarEntity car = carRepository.findById(carId)
                .orElseThrow(() -> new ObjectNotFoundException("Car with:" + carId + " was not found!"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        Double price = calculateRentalPrice(start, end, car.getPricePerDay());
        return price;
    }


    @Override
    @Transactional
    public String addRental(String username,AddRentalDto addRentalDto,Long carId) {

        RentalEntity rental = new RentalEntity();
        CarEntity currentCar = this.carService.getCarById(carId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(addRentalDto.getStartTime(), formatter);
        LocalDateTime end = LocalDateTime.parse(addRentalDto.getEndTime(), formatter);

        rental.setStatus(this.statusService.findByStatus(StatusEnum.Reserved));


        for(RentalEntity r : currentCar.getCarRental()){
            if(StatusEnum.Active.equals(r.getStatus().getStatus()) ||
                    StatusEnum.Reserved.equals(r.getStatus().getStatus()) ||
                    StatusEnum.Late.equals(r.getStatus().getStatus())) {
                if(r.getEndTime().isAfter(start)
                        && r.getStartTime().isBefore(end)) {

                    return "Your selected car was reserved.";
                }
            }
        }

        rental.setStartTime(start);
        rental.setEndTime(end);

        LocalDateTime currentTime = LocalDateTime.now();

        UserEntity renter = this.userService.findUserByName(username);
        rental.setRenter(renter);
        rental.setRentedCar(currentCar);
        rental.setStatus(this.statusService.findByStatus(StatusEnum.Reserved));

        if (rental.getStartTime().isBefore(currentTime.plusHours(1))) {
            return "Cannot make a reservation 1 hour or less before your current time!";
        }

        double price = this.calculateRentalPrice
                (start, end, currentCar.getPricePerDay());


        List<RentalEntity> activeUserReservations =
                this.rentalRepository.findByRenterUserNameActive(renter.getUsername());
        double totalReservationCost = activeUserReservations
                .stream()
                .mapToDouble(RentalEntity::getTotalPrice)
                .sum() + price;

        if (renter.getBudget() > totalReservationCost) {

            rental.setTotalPrice(price);
            this.rentalRepository.save(rental);

        } else {

            return "Not enough money for reservation!";

        }

        this.rentalRepository.save(rental);
        this.carRepository.save(currentCar);
        return "Everything was successful.";

    }


    @Override
    public String completeRental(Long rentalId) {

        RentalEntity rental = this.findById(rentalId);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = rental.getStartTime();
        LocalDateTime endTime = rental.getEndTime();

        UserEntity renter = rental.getRenter();
        CarEntity  car = rental.getRentedCar();

        double balance = renter.getBudget();

        double totalPrice = calculateRentalPrice(
                rental.getStartTime(),
                rental.getEndTime(),
                car.getPricePerDay());

        double payment = 0.0;
        double discount = this.calculateDiscount(renter.getScore());

        long rentalDays = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());
        long currentRentalDays = ChronoUnit.DAYS.between(startTime.toLocalDate(), currentTime.toLocalDate());

        if (currentTime.isBefore(startTime.minusHours(1))) {

            rental.setStatus(this.statusService.findByStatus(StatusEnum.Canceled));
            this.rentalRepository.save(rental);
            return "Rental canceled, charged: 0";

        } else if (currentRentalDays < (rentalDays / 2)) {

            payment = totalPrice / 2.0;
            if (balance < payment) {
                return "Not enough money to complete the rental";
            }
           rental.setStatus(this.statusService.findByStatus(StatusEnum.CompletedEarly));
            this.rentalRepository.save(rental);

        } else {

            if (balance < payment) {

                return "Not enough money to complete the rental";

            }

            payment = totalPrice;
            if(rental.getStatus().getStatus().equals(StatusEnum.Late)) {
                rental.setStatus(this.statusService.findByStatus(StatusEnum.CompletedLate));
            } else {
                rental.setStatus(this.statusService.findByStatus(StatusEnum.CompletedOnTime));
            }

            Double total = balance - (payment - (payment * discount));
            renter.setBudget(total);
            renter.setScore(calculateUserScore(renter.getUsername()));
            rental.setTotalPrice(total);
        }

           this.userRepository.save(renter);
           this.rentalRepository.save(rental);

           return "Rental completed!";
    }

    private double calculateDiscount(double renterScore){

        if(renterScore < 1.0){

            return 0.0;

        }
        else if(renterScore >= 1.0 && renterScore <= 1.25){

            return 0.1;

        }
        else {

            return 0.15;

        }
    }

    @Override
    public HashMap<String,Double> rentalCostSummary(Long rentalId){

        RentalEntity r = this.getRentalById(rentalId);
        UserEntity user = r.getRenter();

        //discount
        double discount = calculateDiscount(user.getScore());
        //totalPrice with discount
        double totalWithDiscount = r.getTotalPrice();
        //calculate price without discount
        double totalWithoutDiscount = totalWithDiscount/(1-discount);

        HashMap<String,Double> summary = new LinkedHashMap<>();

        summary.put("without discount",totalWithoutDiscount);
        summary.put("discount",discount);
        summary.put("total",totalWithDiscount);
        return summary;
    }

    @Override
    public List<RentalForAdminDto> findRentalsInfoForAdmin() {
        List<RentalEntity> all = this.rentalRepository.findAll();
        List<RentalForAdminDto> forAdmin = new ArrayList<>();
        if(!all.isEmpty()){
            for(RentalEntity r : all){
                forAdmin.add(
                        new RentalForAdminDto(
                                r.getRenter().getUsername(),
                                r.getRentedCar().getMake() + r.getRentedCar().getModel(),
                                r.getRentedCar().getRegistrationPlate(),
                                r.getStatus().getStatus(),
                                r.getTotalPrice()

                        )
                );
            }
        }
        return forAdmin;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startTime, LocalDateTime endTime, double pricePerDay) {
        LocalDateTime endTimeWithGracePeriod = endTime.plusHours(2);
        LocalDateTime currentTime = LocalDateTime.now();

        long rentalDays = startTime.until(endTimeWithGracePeriod, ChronoUnit.DAYS);

        if (startTime.plusDays(rentalDays).isAfter(endTimeWithGracePeriod)) {
            rentalDays--;
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
    public List<RentalCarDto> getUserRentalHistory(String username,boolean active) {

        UserEntity currentUser = this.userService.findUserByName(username);
        List<RentalEntity> currentRental = currentUser.getRentals();
        List<RentalCarDto> userRentalHistory = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for(RentalEntity r : currentRental){
            if(active == true){
                if(r.getStatus().getStatus().equals(StatusEnum.Active)
                        || r.getStatus().getStatus().equals(StatusEnum.Reserved)
                        || r.getStatus().getStatus().equals(StatusEnum.Late)){
                    userRentalHistory.add(
                            new RentalCarDto(
                                    r.getId(),
                                    r.getRentedCar().getMake() + " " + r.getRentedCar().getModel(),
                                    r.getStartTime().format(formatter),
                                    r.getEndTime().format(formatter),
                                    r.getTotalPrice(),
                                    r.getStatus().getStatus()
                            )
                    );
                }

            }else{
                if(!r.getStatus().getStatus().equals(StatusEnum.Active)
                        && !r.getStatus().getStatus().equals(StatusEnum.Reserved)
                        && !r.getStatus().getStatus().equals(StatusEnum.Late)){
                    userRentalHistory.add(
                            new RentalCarDto(
                                    r.getId(),
                                    r.getRentedCar().getMake() + " " + r.getRentedCar().getModel(),
                                    r.getStartTime().format(formatter),
                                    r.getEndTime().format(formatter),
                                    r.getTotalPrice(),
                                    r.getStatus().getStatus()
                            )
                    );
                }
            }

        }
        return userRentalHistory;

    }

    @Override
    public Double calculateMonthlyRevenue(int month,int year) {
        List<RentalEntity> rentals =
                this.rentalRepository.findAllFinishedRentalsForCurrentMonth(month,year);

        double total = 0.0;
        for(RentalEntity r : rentals){
            total += r.getTotalPrice();
        }

        return total;
    }

    @Override
    public void changeStatus(Long id) {
        RentalEntity rental = this.findById(id);
        if(StatusEnum.Active.equals(rental.getStatus().getStatus())
        & StatusEnum.Reserved.equals(rental.getStatus().getStatus())){
            rental.setStatus(this.statusService.findByStatus(StatusEnum.Canceled));
        }
        this.rentalRepository.save(rental);
    }

    @Override
    public RentalEntity findById(Long id) {

        return this.rentalRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Rental with id=" + id + " was not found")
        );

    }

    @Override
    public Double calculateUserScore(String username){

        Double userScore = 0.0;
        Double sumScore = 0.0;
        List<RentalEntity> listRentalsScore=
                this.rentalRepository.findByRenterUsernameWithStatusCompleted(username);
        int size = listRentalsScore.size();
        if(!listRentalsScore.isEmpty() && size>2){
            for(RentalEntity r:listRentalsScore){
                sumScore += r.getStatus().getScore();

            }

            userScore = sumScore / size;

        }

        return userScore;
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