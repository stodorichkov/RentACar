package com.example.demo.repository;

import com.example.demo.model.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
}
