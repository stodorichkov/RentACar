package com.example.demo.repository;

import com.example.demo.model.StatusEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {

    @Query("SELECT s FROM StatusEntity s WHERE s.status = :status")
    Optional<StatusEntity> findByStatus(@Param("status") StatusEnum status);
}
