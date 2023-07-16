package com.example.MyTest_Spring.repository;


import com.example.MyTest_Spring.entity.Reservation;
import com.example.MyTest_Spring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

    @Query("SELECT r FROM Reservation r WHERE r.Parking_ID = :Parking_ID")
    Optional<Reservation> getReservationByParkingId(@Param("Parking_ID") int Parking_ID);

    @Modifying
    @Query("DELETE FROM Reservation WHERE Parking_ID = :Parking_ID AND User_ID = :User_ID")
    void deleteReservationById(@Param("Parking_ID") int Parking_ID,@Param("User_ID") int User_ID);

}
