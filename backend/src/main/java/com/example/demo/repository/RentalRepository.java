package com.example.demo.repository;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    @Query("SELECT r FROM RentalEntity r WHERE r.renter.username = :username")
    List<RentalEntity> findByRenterUsername(String username);


    @Query("SELECT r FROM RentalEntity r WHERE r.renter.username = :username AND r.status != 'completed'")
    List<RentalEntity>findByRenterUserNameActive(String username);
    //Select * from RentalEntity join UserEntity u where u.username := username


    @Query("SELECT r FROM RentalEntity r JOIN r.car car WHERE car.isRented = false AND " +
            "MONTH(r.endTime) = :month")
    List<RentalEntity> findAllFinishedRentalsForCurrentMonth(int month);

}
