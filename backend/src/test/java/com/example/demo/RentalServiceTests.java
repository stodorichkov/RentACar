package com.example.demo;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.StatusEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.enums.StatusEnum;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.implementation.RentalServiceImpl;
import com.example.demo.service.service.CarService;
import com.example.demo.service.service.StatusService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
 class RentalServiceTest {

    @InjectMocks
    private RentalServiceImpl rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private StatusService statusService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarService carService;

    // Add other necessary mocks and dependencies

    @Test
    public void testCompleteRental_CanceledBeforeStartTime() {
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.plusHours(1).plusMinutes(30);
        LocalDateTime endTime = currentTime.plusHours(2);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);

        UserEntity renter = new UserEntity();
        renter.setBudget(1000.0);

        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.Canceled)).thenReturn(new StatusEntity(StatusEnum.Canceled));

        // Act
        String result = rentalService.completeRental(rentalId);

        // Assert
        Assert.assertEquals("Rental canceled ,charged: 0", result);
        Assert.assertEquals(StatusEnum.Canceled, rental.getStatus().getStatus());
    }


    @Test
    public void testCompleteRental_CancelAfterStartTime(){
        // Arrange
        Long rentalId = 1L;
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = currentTime.plusHours(1).plusMinutes(30);
        LocalDateTime endTime = currentTime.plusHours(2);

        RentalEntity rental = new RentalEntity();
        rental.setId(rentalId);
        rental.setStartTime(startTime);
        rental.setEndTime(endTime);

        UserEntity renter = new UserEntity();
        renter.setBudget(1000.0);

        Mockito.when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        Mockito.when(rentalRepository.save(Mockito.any(RentalEntity.class))).thenReturn(rental);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(renter);
        Mockito.when(statusService.findByStatus(StatusEnum.Canceled)).thenReturn(new StatusEntity(StatusEnum.Canceled));

        // Act
        String result = rentalService.completeRental(rentalId);

        // Assert
        Assert.assertEquals("Rental canceled ,charged: 0", result);
        Assert.assertEquals(StatusEnum.Canceled, rental.getStatus().getStatus());
    }


    // Add more test cases for other scenarios

}
