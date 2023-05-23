package com.example.demo.repository;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
   List<CarEntity> findByIsRented(Boolean isRented);

   Optional<CarEntity> findByRegistrationPlate(String registrationPlate);

   @Query("SELECT c FROM CarEntity c LEFT JOIN c.carRental r WHERE :reservationDate NOT BETWEEN r.startTime AND r.endTime")
   List<CarEntity> findAvailableCarsByReservationDate(@Param("reservationDate") LocalDate reservationDate);
}
