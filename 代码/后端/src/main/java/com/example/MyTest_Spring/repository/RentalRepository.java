package com.example.MyTest_Spring.repository;


import com.example.MyTest_Spring.entity.Rental;
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
public interface RentalRepository extends JpaRepository<Rental, Integer>  {

    @Query("SELECT r FROM Rental r WHERE r.User_ID = :User_ID")
    List<Rental> getHistoryRentals(@Param("User_ID") int User_ID);
}
