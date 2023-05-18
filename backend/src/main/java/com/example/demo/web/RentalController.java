package com.example.demo.web;

import com.example.demo.model.dto.RentalDto;
import com.example.demo.model.RentalEntity;
import com.example.demo.service.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RentalEntity>> getAllRentals() {
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalEntity> getRentalById(@PathVariable Long id) {
        return new ResponseEntity<>(rentalService.getRentalById(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RentalEntity> addRental(@RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.addRental(rentalDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<RentalEntity> updateRental(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.updateRental(id, rentalDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
