package com.example.MyTest_Spring.controller;

import com.example.MyTest_Spring.entity.Reservation;
import com.example.MyTest_Spring.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.time.LocalTime;
import java.time.OffsetDateTime;
@RestController
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservationList")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    @PostMapping("/parking/getstarttime")
    public Time getStartTime(@RequestBody Map<String, String> request) {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        Optional<Reservation> optionalReservation = reservationService.getReservationByParkingId(parkingId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            return reservation.getStartTime();
        } else {
            return null; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parking/getendtime")
    public Time getEndTime(@RequestBody Map<String, String> request) {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        Optional<Reservation> optionalReservation = reservationService.getReservationByParkingId(parkingId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            return reservation.getEndTime();
        } else {
            return null; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parking/getuserid")
    public int getUserId(@RequestBody Map<String, String> request) {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        Optional<Reservation> optionalReservation = reservationService.getReservationByParkingId(parkingId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            return reservation.getUserID();
        } else {
            return 0; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parking/addreservation")
    public ResponseEntity<Reservation> addReservation(@RequestBody Map<String, String> request) throws ParseException {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        int userId = Integer.parseInt(request.get("User_ID"));

        String timeString1 = request.get("Start_Time");
        String timeString2 = request.get("End_Time");
        OffsetDateTime dateTime1 = OffsetDateTime.parse(timeString1);
        OffsetDateTime dateTime2 = OffsetDateTime.parse(timeString2);
        Time startTime = Time.valueOf(dateTime1.toLocalTime());
        Time endTime = Time.valueOf(dateTime2.toLocalTime());

        Reservation reservation = new Reservation(parkingId,userId,startTime,endTime);
        reservationService.createReservation(reservation);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/parking/deletereservation")
    public void deleteReservation(@RequestBody Map<String, String> request) {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        int userId = Integer.parseInt(request.get("User_ID"));
        reservationService.deleteReservationById(parkingId,userId);
    }
    @PostMapping("/reservation")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

//    @PutMapping("/{rentalId}")
//    public Rental updateRental(@PathVariable int rentalId, @RequestBody Rental rental) {
//        rental.setRentalId(rentalId);
//        return rentalService.updateRental(rental);
//    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.deleteReservation(reservationId);
    }

    // 处理其他自定义的API请求...
}
