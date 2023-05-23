package com.example.demo.repository;

import com.example.demo.model.RentalEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, Long> {

    @Query("SELECT r FROM RentalEntity r WHERE r.renter.username = :username")
    List<RentalEntity> findByRenterUsername(String username);


    @Query("SELECT r FROM RentalEntity r WHERE r.renter.username = :username AND r.status != 'completed'")
    List<RentalEntity>findByRenterUserNameActive(String username);
    //Select * from RentalEntity join UserEntity u where u.username := username



    @Query("SELECT r FROM RentalEntity r JOIN r.rentedCar rentedCar WHERE rentedCar.isRented = false AND " +
            "MONTH(r.endTime) = :month AND YEAR(r.endTime) = :year")
    List<RentalEntity> findAllFinishedRentalsForCurrentMonth(@Param("month") int month,
                                                             @Param("year") int year);

    @Query("SELECT r FROM RentalEntity r JOIN r.status s WHERE s.status = :currentStatus")
    List<RentalEntity> findAllByStatus(@Param("currentStatus") StatusEnum currentStatus);

}
