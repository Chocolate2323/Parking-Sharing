package com.example.MyTest_Spring.controller;

import com.example.MyTest_Spring.entity.Parking;
import com.example.MyTest_Spring.entity.Users;
import com.example.MyTest_Spring.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class ParkingController {

    private final ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/parking/parkingList")
    public List<Parking> getAllParkingLots() {
        return parkingService.getAllParkingLots();
    }

//    @GetMapping("/{parkingId}")
//    public Parking getParkingById(@PathVariable int parkingId) {
//        return parkingService.getParkingById(parkingId);
//    }

    @PostMapping("/parking/changestates1")
    public void updateParkingStatus1(@RequestBody Map<String, Integer> request) {
        int parkingID = request.get("Parking_ID");
        parkingService.updateParkingStatus(parkingID, "已预定");
    }

    @PostMapping("/parking/changestates2")
    public void updateParkingStatus2(@RequestBody Map<String, Integer> request) {
        int parkingID = request.get("Parking_ID");
        parkingService.updateParkingStatus(parkingID, "空闲");
    }
    @PostMapping("/parking/getcommunityid")
    public int getCommunityId(@RequestBody Map<String, String> request) {
        String parking_ID = request.get("Parking_ID");
        Optional<Parking> optionalParking = parkingService.getParkingById(parking_ID);

        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            return parking.getCommunityID();
        } else {
            return 0; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parking/getparkingnumber")
    public String getParkingNumber(@RequestBody Map<String, String> request) {
        String parking_ID = request.get("Parking_ID");
        Optional<Parking> optionalParking = parkingService.getParkingById(parking_ID);

        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            return parking.getParkingNumber();
        } else {
            return ""; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parkingList/searchnumber")
    public List<Parking> searchNumber(@RequestBody Map<String, String> request) {
        String Parking_Number = request.get("Parking_Number");
        return parkingService.findByParkingNumberContaining(Parking_Number);
    }
    @PostMapping("/parkingList/searchid")
    public ResponseEntity<Parking> searchID(@RequestBody Map<String, String> request) {
        String Parking_ID = request.get("Parking_ID");
        Optional<Parking> optionalParking = parkingService.getParkingById(Parking_ID);
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            // 构建用户详细信息对象
            Parking parkingDetails = new Parking();
            parkingDetails.setParkingID(parking.getParkingID());
            parkingDetails.setParkingNumber(parking.getParkingNumber());
            parkingDetails.setStates(parking.getStates());
            parkingDetails.setArea(parking.getArea());
            parkingDetails.setPrice(parking.getPrice());
            parkingDetails.setCommunityID(parking.getCommunityID());

            return ResponseEntity.ok(parkingDetails);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/parkingUpdate1")
    public ResponseEntity<Parking> updateParking(@RequestBody Map<String, String> request) {
        String Parking_ID = request.get("Parking_ID");
        String Community_ID = request.get("Community_ID");
        String ParkingNumber = request.get("ParkingNumber");
        String Price = request.get("Price");
        String Area = request.get("Area");
        String States = request.get("States");
        Optional<Parking> optionalParking = parkingService.getParkingById(Parking_ID);
        if (optionalParking.isPresent()) {
            Parking existingParking = optionalParking.get();
            existingParking.setCommunityID(Integer.parseInt(Community_ID));
            existingParking.setParkingNumber(ParkingNumber);
            existingParking.setPrice(Integer.parseInt(Price));
            existingParking.setArea(Float.parseFloat(Area));
            existingParking.setStates(States);
            // 保存到数据库
            parkingService.saveParking(existingParking);

            return ResponseEntity.ok(existingParking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/addparking1")
    public ResponseEntity<Parking> addParking(@RequestBody Map<String, String> request) {
        int Community_ID = Integer.parseInt(request.get("Community_ID"));
        String ParkingNumber = request.get("ParkingNumber");
        int Price = Integer.parseInt(request.get("Price"));
        float Area = Float.parseFloat(request.get("Area"));
        String States = request.get("States");
        Parking newParking = new Parking(ParkingNumber,Community_ID,Area,States,Price);
        // 保存用户到数据库
        parkingService.saveParking(newParking);
        return ResponseEntity.ok(newParking);
    }
    @PostMapping("/deleteparking")
    public ResponseEntity<String> deleteParking(@RequestBody Map<String, String> request) {
        int Parking_ID = Integer.parseInt(request.get("Parking_ID"));
        parkingService.deleteParking(Parking_ID);
        return ResponseEntity.ok("车位删除成功");
    }
    @PostMapping("/parking")
    public Parking createParking(@RequestBody Parking parking) {
        return parkingService.createParking(parking);
    }


    @DeleteMapping("/{parkingId}")
    public void deleteParking(@PathVariable int parkingId) {
        parkingService.deleteParking(parkingId);
    }

    // 处理其他自定义的API请求...
}
