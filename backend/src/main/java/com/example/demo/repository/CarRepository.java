package com.example.demo.repository;

import com.example.demo.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
   List<CarEntity> findCarByIsRented(Boolean isRented);
}
