package com.example.MyTest_Spring.repository;

import com.example.MyTest_Spring.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    @Query("SELECT p FROM Parking p WHERE p.Parking_Number LIKE %:Parking_Number%")
    List<Parking> findByParkingNumberContaining(@Param("Parking_Number") String Parking_Number);
}
