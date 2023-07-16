package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;


import com.example.MyTest_Spring.entity.Parking;
import com.example.MyTest_Spring.entity.Users;
import com.example.MyTest_Spring.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;
    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public List<Parking> getAllParkingLots() {
        return parkingRepository.findAll();
    }


    public Parking createParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Parking updateParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public void deleteParking(int parkingId) {
        parkingRepository.deleteById(parkingId);
    }

    public void updateParkingStatus(int parkingID, String status) {
        Optional<Parking> optionalParking = parkingRepository.findById(parkingID);
        if (optionalParking.isPresent()) {
            Parking parking = optionalParking.get();
            parking.setStates(status);
            parkingRepository.save(parking);
        }
    }
    public Optional<Parking> getParkingById(String parkingId) {
        return parkingRepository.findById(Integer.parseInt(parkingId));
    }
    public void saveParking(Parking parking) {
        parkingRepository.save(parking);
    }
    public List<Parking> findByParkingNumberContaining(String Parking_Number) {
        return parkingRepository.findByParkingNumberContaining(Parking_Number);
    }

}
