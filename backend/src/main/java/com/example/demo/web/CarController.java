package com.example.demo.web;

import com.example.demo.model.dto.CarAdminDto;
import com.example.demo.model.dto.CarDto;
import com.example.demo.model.dto.CarEnumDto;
import com.example.demo.model.dto.EditConditionAndPriceDto;
import com.example.demo.service.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;

    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/all-unique")
    public ResponseEntity<Set<CarDto>> getAllUnique(){

        Set<CarDto> uniqueCars = this.carService.getAllUniqueCars();
        if(uniqueCars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(uniqueCars);
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CarAdminDto>> allCarsForAdmin(Authentication authentication){
        List<CarAdminDto> cars = this.carService.findCarsForAdmin(authentication.getName());
        if(!cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(cars);
        }
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCars(@PathVariable Long id){
        this.carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addCar(@RequestBody CarDto carDto,
                                         Authentication authentication){
        return ResponseEntity.ok(this.carService.addCar(carDto,authentication.getName()));
    }

    @PatchMapping("/{carId}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> editCar(@PathVariable Long carId,
                                          @RequestBody EditConditionAndPriceDto editConditionAndPriceDto){
        String response = this.carService.editConditionAndPrice(editConditionAndPriceDto,carId);
        if(!response.equals("Edit was successful!")){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }






}
