package com.example.demo.service.implementation;

import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.RentalEntity;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.RentalRepository;
import com.example.demo.service.service.RentalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
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

        List<CarEntity> cars = rentalDto.getRentedCars().stream()
                .map(carDto -> carRepository.findById(carDto.getId()).orElseThrow(() -> new RuntimeException("Car not found")))
                .collect(Collectors.toList());

        rental.setRentedCars(cars);
        return rentalRepository.save(rental);
    }

    @Override
    public RentalEntity updateRental(Long id, RentalDto rentalDto) {
        RentalEntity rental = rentalRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Rental not found"));
        rental.setStartTime(rentalDto.getStartTime());
        rental.setEndTime(rentalDto.getEndTime());
        rental.setTotalPrice(rentalDto.getTotalPrice());

        List<CarEntity> cars = rentalDto.getRentedCars().stream()
                .map(carDto -> carRepository.findById(carDto.getId()).orElseThrow(() -> new ObjectNotFoundException("Car not found")))
                .collect(Collectors.toList());

        rental.setRentedCars(cars);
        return rentalRepository.save(rental);
    }


    @Override
    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}

