package com.example.demo.web;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;
import com.example.demo.service.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> getAll(){

        return ResponseEntity.ok(this.carService.getAllCars());
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<CarDto> carInfo(@PathVariable Long id){
        CarDto car = this.carService.getCarInfo(id);
        return ResponseEntity.ok(car);
    }

    @GetMapping("/enums")
    public ResponseEntity<CarEnumDto> carEnum(){
        return ResponseEntity.ok(this.carService.findCarEnumInfo());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteCars(@PathVariable Long id){
        this.carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody CarDto carDto,
                                         Principal principal){
        return ResponseEntity.ok(this.carService.addCar(carDto,principal));
    }


}
