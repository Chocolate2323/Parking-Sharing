package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;

import com.example.MyTest_Spring.entity.Rental;
import com.example.MyTest_Spring.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RentalService  {

    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }


    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getHistoryRentals(int userId) {
        return rentalRepository.getHistoryRentals(userId);
    }
    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }


    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }


    public void deleteRental(int rentalId) {
        rentalRepository.deleteById(rentalId);
    }
}
