package com.example.demo.repository;

import com.example.demo.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
   List<CarEntity> findByIsRented(Boolean isRented);

   Optional<CarEntity> findByRegistrationPlate(String registrationPlate);


   @Query("SELECT c FROM CarEntity c JOIN c.carRental r " +
           "JOIN r.status s WHERE r.startTime = :start AND r.endTime = :end AND s.status" +
           " IN('CompletedOnTime', 'CompletedLate', 'CompletedEarly', 'Canceled'")
    List<CarEntity> findAllByTheirAvailability(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
