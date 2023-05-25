package com.example.demo.web;

import com.example.demo.model.dto.*;
import com.example.demo.model.RentalEntity;
import com.example.demo.service.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/history")
    public ResponseEntity<List<RentalCarDto>> getUserHistory(Authentication authentication){
        List<RentalCarDto> rental = this.rentalService.getUserRentalHistory(authentication.getName());
        if(rental.isEmpty()){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/{month}/{year}/total")
    public ResponseEntity<Double> getMonthlyRevenue(@PathVariable int month,
                                                    @PathVariable int year) {
        return ResponseEntity.ok(this.rentalService.calculateMonthlyRevenue(month,year));
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

    @GetMapping("/showCost")
    public ResponseEntity<Double> calculateRentalPrice(@RequestBody ShowRentalCostDto showRentalCostDto) {
        return new ResponseEntity<>(rentalService.showTotalCost(showRentalCostDto), HttpStatus.OK);
    }



    @PatchMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id){
        this.rentalService.changeStatus(id);
        return ResponseEntity.noContent().build();
    }
}
