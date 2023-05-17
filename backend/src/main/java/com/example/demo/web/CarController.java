package com.example.demo.web;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
import com.example.demo.service.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;

    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }
    @GetMapping
    public List<CarDto> getAll(){
        List<CarDto> cars = new ArrayList<>();
        for(CarEntity car : carService.getAllCars()){
            cars.add(modelMapper.map(car,CarDto.class));
        }
        return cars;
    }

    @GetMapping("/{brand}/{model}/carInfo")
    public ResponseEntity<CarDto> carInfo(String brand, String model){
        return ResponseEntity.ok(this.carService.getCarInfo(brand,model));

    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteCars(@PathVariable Long id){
        this.carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
