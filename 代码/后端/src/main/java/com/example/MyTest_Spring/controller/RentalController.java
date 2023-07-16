package com.example.MyTest_Spring.controller;

import com.example.MyTest_Spring.entity.Rental;
import com.example.MyTest_Spring.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rentalList")
    public List<Rental> getAllRentals(){
        return rentalService.getAllRentals();
    }

    @PostMapping("/history")
    public List<Rental> getHistoryRentals(@RequestBody Map<String, String> request) {
        int userId = Integer.parseInt(request.get("User_ID"));
        return rentalService.getHistoryRentals(userId);
    }

//    @GetMapping("/{rentalId}")
//    public Rental getRentalById(@PathVariable int rentalId) {
//        return rentalService.getRentalById(rentalId);
//    }

    @PostMapping("/parking/addrental")
    public ResponseEntity<Rental> addRental(@RequestBody Map<String, String> request) throws ParseException {
        int parkingId = Integer.parseInt(request.get("Parking_ID"));
        int userId = Integer.parseInt(request.get("User_ID"));
        int price = Integer.parseInt(request.get("Price"));

        String timeString1 = request.get("Start_Time");
        String timeString2 = request.get("End_Time");
        OffsetDateTime dateTime1 = OffsetDateTime.parse(timeString1);
        OffsetDateTime dateTime2 = OffsetDateTime.parse(timeString2);
        Time startTime = Time.valueOf(dateTime1.toLocalTime());
        Time endTime = Time.valueOf(dateTime2.toLocalTime());

        Rental rental = new Rental(parkingId,userId,price,startTime,endTime);
        // 保存用户到数据库
        rentalService.createRental(rental);
        return ResponseEntity.ok(rental);
    }
    @PostMapping("/rental")
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }

//    @PutMapping("/{rentalId}")
//    public Rental updateRental(@PathVariable int rentalId, @RequestBody Rental rental) {
//        rental.setRentalId(rentalId);
//        return rentalService.updateRental(rental);
//    }

    @DeleteMapping("/{rentalId}")
    public void deleteRental(@PathVariable int rentalId) {
        rentalService.deleteRental(rentalId);
    }

    // 处理其他自定义的API请求...
}
