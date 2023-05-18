package com.example.demo.repository;

import com.example.demo.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
   List<CarEntity> findByIsRented(Boolean isRented);

   Optional<CarEntity> findByRegistrationPlate(String registrationPlate);
}
