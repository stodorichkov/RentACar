package com.example.demo.web;

import com.example.demo.model.dto.*;
import com.example.demo.service.service.RentalService;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.service.CarService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    private final CarService carService;

    public RentalController(RentalService rentalService, CarService carService) {
        this.rentalService = rentalService;
        this.carService = carService;
    }

    @GetMapping("/history")
    public ResponseEntity<List<RentalCarDto>> getUserHistory(Authentication authentication){
        List<RentalCarDto> rental = this.rentalService.getUserRentalHistory(authentication.getName(),false);
        if(rental.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/active-history")
    public ResponseEntity<List<RentalCarDto>> getUserActiveHistory(Authentication authentication){
        List<RentalCarDto> activeRental = this.rentalService.getUserRentalHistory(authentication.getName(),true);
        if(activeRental.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(activeRental);
    }

    @GetMapping("/{month}/{year}/total")
    public ResponseEntity<Double> getMonthlyRevenue(@PathVariable int month,
                                                    @PathVariable int year) {
        return ResponseEntity.ok(this.rentalService.calculateMonthlyRevenue(month,year));
    }

    @GetMapping("/all-unique-available")
    public ResponseEntity<Set<CarDto>> getUniqueAvailableCarsByDate(@RequestParam("startDate") String startDate,
                                                                    @RequestParam("endDate") String endDate) {

        Set<CarDto> uniqueCars = this.carService.getUniqueAvailableCarsByDate(startDate,endDate);
        if(uniqueCars.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        //combine Make and Model for front-end temporary:
        //for(CarDto c : uniqueCars){c.setMakeModel(c.getMake()+" "+c.getModel());}
        return ResponseEntity.ok(uniqueCars);
    }

    @PostMapping("/{carId}/add")
    public ResponseEntity<String> addRental(Authentication authentication,@RequestBody AddRentalDto addrentalDto,
                                                  @PathVariable Long carId
                                                 ) {
        String response = this.rentalService.addRental(authentication.getName(),addrentalDto,carId);
        if(!response.equals("Everything was successful.")){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{rentalId}/complete")
    public ResponseEntity<String> completeRental(@PathVariable Long rentalId){
        String response = this.rentalService.completeRental(rentalId);
        if(!response.equals("Rental completed!")){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{rentalId}/summary")
    public ResponseEntity<HashMap<String,Double>> summary(@PathVariable Long rentalId){
        HashMap<String,Double> map = this.rentalService.rentalCostSummary(rentalId);
        if(map.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{carId}/showCost")
    public ResponseEntity<Double> calculateRentalPrice(@RequestParam("startDate") String startDate,
                                                       @RequestParam("endDate") String endDate,
                                                       @PathVariable Long carId) {
        return new ResponseEntity<>(rentalService.showTotalCost(startDate,endDate,carId), HttpStatus.OK);
    }


    @PatchMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id){
        this.rentalService.changeStatus(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<RentalForAdminDto>> rentalForAdminInfo(){

        List<RentalForAdminDto> forAdmin = this.rentalService.findRentalsInfoForAdmin();
        if(forAdmin.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(forAdmin);

    }

}
