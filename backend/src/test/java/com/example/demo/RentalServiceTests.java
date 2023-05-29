package com.example.demo;

import com.example.demo.model.CarEntity;
import com.example.demo.model.RentalEntity;
import com.example.demo.model.StatusEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.enums.StatusEnum;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.schedule.StatusScheduler;
import com.example.demo.service.implementation.RentalServiceImpl;
import com.example.demo.service.service.CarService;

import com.example.demo.service.service.StatusService;
import com.example.demo.service.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(MockitoJUnitRunner.class)
 class RentalServiceTest {


    private RentalServiceImpl rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private StatusService statusService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserService userService;
    @Mock
    private CarService carService;

    @Mock
    private StatusScheduler statusScheduler;
    // Add other necessary mocks and dependencies

    @BeforeEach
    void init(){

        MockitoAnnotations.initMocks(this);
        this.rentalService = new RentalServiceImpl(rentalRepository, carRepository, userRepository,
                statusService, userService, carService);

    }
    @Test
    public void testCompleteRental_CancelBeforeStartTime(){
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.plusHours(1).plusMinutes(30);
        LocalDateTime endTime = currentTime.plusHours(48);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);

        UserEntity renter = new UserEntity();
        renter.setScore(1.5);
        renter.setBudget(1000.0);

        CarEntity car = new CarEntity();
        car.setPricePerDay(60.00);

        rental.setRenter(renter);
        rental.setRentedCar(car);


        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.Canceled)).thenReturn(new StatusEntity(StatusEnum.Canceled));

        // act
        String result = rentalService.completeRental(rentalId);

        // assert
        Assert.assertEquals("Rental canceled, charged: 0", result);
        //Assert.assertNull(rental.getStatus());
    }


    // Add more test cases for other scenarios

    @Test
    public void testCompleteRental_CompleteAfterStartTime(){
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(24).plusMinutes(30);
        LocalDateTime endTime = currentTime.plusHours(48);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);

        rental.setStatus(new StatusEntity(StatusEnum.Active));
        UserEntity renter = new UserEntity();
        renter.setScore(1.5);
        renter.setBudget(1000.0);

        CarEntity car = new CarEntity();
        car.setPricePerDay(60.00);

        rental.setRenter(renter);
        rental.setRentedCar(car);


        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.CompletedEarly)).thenReturn(new StatusEntity(StatusEnum.CompletedEarly));
        //StatusEnum stats = rental.getStatus().getStatus();// assert
        // act
        String result = rentalService.completeRental(rentalId);

        Assert.assertEquals("Rental completed!",result );
        //Assert.assertNull(rental.getStatus());
    }

    @Test
    public void testCompleteRental_CompleteAfterStartTimeHalf(){
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(24).plusMinutes(30);
        LocalDateTime endTime = currentTime.plusHours(23);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);
        rental.setStatus(new StatusEntity(StatusEnum.Active));

        UserEntity renter = new UserEntity();
        renter.setScore(1.5);
        renter.setBudget(1000.0);

        CarEntity car = new CarEntity();
        car.setPricePerDay(60.00);

        rental.setRenter(renter);
        rental.setRentedCar(car);


        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.CompletedOnTime)).thenReturn(new StatusEntity(StatusEnum.CompletedOnTime));
        //StatusEnum stats = rental.getStatus().getStatus();// assert
        // act
        String result = rentalService.completeRental(rentalId);

        Assert.assertEquals("Rental completed!",result );
        //Assert.assertNull(rental.getStatus());
    }

    @Test
    public void testCompleteRental_CompleteAfterEndTime() throws InterruptedException {
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.minusHours(48).plusMinutes(30);
        LocalDateTime endTime = currentTime.minusHours(15);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);

        rental.setStatus(new StatusEntity(StatusEnum.Reserved));
        // Create a CountDownLatch with a count of 1
        CountDownLatch latch = new CountDownLatch(1);

        // Mock the behavior of the statusScheduler
        StatusScheduler statusScheduler = new StatusScheduler(rentalRepository, statusService) {
            @Override
            public void scheduled() {
                super.scheduled();
                latch.countDown(); // Signal completion of the scheduler execution
            }
        };

        latch.await(1, TimeUnit.MINUTES);
        //rental.setStatus(new StatusEntity(StatusEnum.Active));

        UserEntity renter = new UserEntity();
        renter.setScore(1.5);
        renter.setBudget(1000.0);

        CarEntity car = new CarEntity();
        car.setPricePerDay(60.00);

        rental.setRenter(renter);
        rental.setRentedCar(car);




        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.CompletedOnTime)).thenReturn(new StatusEntity(StatusEnum.CompletedOnTime));
        //StatusEnum stats = rental.getStatus().getStatus();// assert
        // act
        String result = rentalService.completeRental(rentalId);

        Assert.assertEquals("Rental completed!",result );
        //Assert.assertNull(rental.getStatus());
    }


}
