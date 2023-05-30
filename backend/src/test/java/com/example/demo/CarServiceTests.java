package com.example.demo;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.dto.CarDto;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.implementation.CarServiceImpl;
import com.example.demo.service.service.CarService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.security.Principal;
import java.util.Optional;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTests {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarService carService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl carServiceImpl;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /*  @Test
    public void itShouldGetAllUniqueCars() {

        CarEntity car1 = new CarEntity();
        car1.setMake("Audi");
        car1.setModel("A7");
        car1.setRegistrationPlate("ABC123");

        CarEntity car2 = new CarEntity();
        car2.setMake("BMW");
        car2.setModel("318");
        car2.setRegistrationPlate("BCD223");

        CarEntity car3 = new CarEntity();
        car3.setMake("Audi");
        car3.setModel("A7");
        car3.setRegistrationPlate("DDC334");

        List<CarEntity> allCars = Arrays.asList(car1, car2, car3);
        when(carRepository.findAll()).thenReturn(allCars);

        List<CarDto> mappedCars = Arrays.asList(new CarDto(), new CarDto());
        when(modelMapper.map(allCars, new TypeToken<List<CarDto>>(){}.getType())).thenReturn(mappedCars);

        Set<CarDto> uniqueCars = carServiceImpl.getAllUniqueCars();

        assertEquals(2,uniqueCars.size());
        assertTrue(uniqueCars.contains(createCarDto("Audi","A7","ABC123")));
        assertTrue(uniqueCars.contains(createCarDto("BMW","318","BCD223")));
    }*/
    @Test
    public void itShouldDeleteCar() {
        // Create a car entity with a specific ID
        Long carId = 1L;
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));

        carServiceImpl.deleteCar(carId);

        verify(carRepository).deleteById(carId);
    }
    @Test
    public void itShouldGetCarInfo() {
        Long carId = 1L;
        String make = "Audi";
        String model = "A7";
        CarEntity carEntity = new CarEntity();
        carEntity.setId(carId);
        carEntity.setMake(make);
        carEntity.setModel(model);

        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));

        CarDto expectedCarDto = new CarDto();
        expectedCarDto.setMake(make);
        expectedCarDto.setModel(model);
        when(modelMapper.map(carEntity, CarDto.class)).thenReturn(expectedCarDto);

        CarDto actualCarDto = carServiceImpl.getCarInfo(carId);

        verify(carRepository).findById(carId);

        verify(modelMapper).map(carEntity, CarDto.class);

        assertNotNull(actualCarDto);
        assertEquals(expectedCarDto, actualCarDto);
    }
    @Test
    public void itShouldGetCarByIdWhenIdExists() {

        Long carId = 1L;
        CarEntity expectedCar = new CarEntity();
        expectedCar.setId(carId);
        when(carRepository.findById(carId)).thenReturn(Optional.of(expectedCar));

        CarEntity actualCar = carServiceImpl.getCarById(carId);

        assertNotNull(actualCar);
        assertEquals(expectedCar, actualCar);
        verify(carRepository, times(1)).findById(carId);
    }
    @Test
    public void itShouldThrowExceptionWhenThereIsNoId() {

        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            carServiceImpl.getCarById(carId);
        });

        verify(carRepository, times(1)).findById(carId);
    }
    @Test
    public void itShouldAddCarWithUniqueRegistrationPlate() {

        CarDto carDto = new CarDto();
        carDto.setMake("Audi");
        carDto.setModel("A7");
        carDto.setRegistrationPlate("ABC123");
        carDto.setImageUrl("https://example.com/image.jpg");
        carDto.setPricePerDay(100.0);

        UserEntity admin = new UserEntity();
        admin.setUsername("admin");

        UserRepository userRepository = mock(UserRepository.class);
        CarRepository carRepository = mock(CarRepository.class);

        CarServiceImpl carService = new CarServiceImpl(carRepository, modelMapper, rentalRepository, userRepository);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(carRepository.findByRegistrationPlate("ABC123")).thenReturn(Optional.empty());

        String result = carService.addCar(carDto, () -> "admin");

        assertEquals("Car is added successfully!", result);
        assertEquals("Audi", admin.getAddedByAdmin().get(0).getMake());
        assertEquals("ABC123", admin.getAddedByAdmin().get(0).getRegistrationPlate());
        verify(userRepository, times(1)).save(admin);
        verify(carRepository, times(1)).save(any(CarEntity.class));
    }


    @Test
    public void itShouldNotAddCarWithAlreadyExistingRegistrationPlate() {

        CarDto carDto = new CarDto();
        carDto.setMake("Audi");
        carDto.setModel("A7");
        carDto.setRegistrationPlate("ABC123");
        carDto.setImageUrl("https://example.com/image.jpg");
        carDto.setPricePerDay(100.0);

        Principal principal = mock(Principal.class);
        UserEntity admin = new UserEntity();
        admin.setUsername("admin");

        UserRepository userRepository = mock(UserRepository.class);
        CarRepository carRepository = mock(CarRepository.class);

        CarServiceImpl carService = new CarServiceImpl(carRepository, modelMapper, rentalRepository, userRepository);

        when(carRepository.findByRegistrationPlate("ABC123")).thenReturn(Optional.of(new CarEntity()));

        String result = carService.addCar(carDto, principal);

        assertEquals("Car with the same registration plate was already added!", result);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(carRepository, never()).save(any(CarEntity.class));
    }
}
