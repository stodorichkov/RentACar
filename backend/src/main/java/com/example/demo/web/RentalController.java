package com.example.demo.web;

import com.example.demo.model.dto.*;
import com.example.demo.model.RentalEntity;
import com.example.demo.service.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public ResponseEntity<String> addRental(@RequestBody AddRentalDto addrentalDto,
                                                  @PathVariable Long carId
                                                 ) {
        String response = this.rentalService.addRental(addrentalDto,carId);
//        if(!response.equals("Everything was successful.")){
//            return ResponseEntity.internalServerError().build();
//        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/completeRental")

    public ResponseEntity<String> completeRental(@RequestBody CompleteRentalDto completeRentalDto) {
        String response = this.rentalService.completeRental(completeRentalDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/showCost")
    public ResponseEntity<Double> calculateRentalPrice(@RequestBody ShowRentalCostDto showRentalCostDto) {
        return new ResponseEntity<>(rentalService.showTotalCost(showRentalCostDto), HttpStatus.OK);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<RentalEntity> updateRental(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.updateRental(id, rentalDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
