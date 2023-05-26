package com.example.demo.repository;

import com.example.demo.model.CarEntity;
import com.example.demo.model.dto.CarDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {

   Optional<CarEntity> findByRegistrationPlate(String registrationPlate);

   @Query("SELECT c from CarEntity c JOIN c.addedByAdmin a where a.username = :user")
   List<CarEntity> findAllForAdmin(@Param("user") String user);

}
