package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;

import com.example.MyTest_Spring.entity.Reservation;
import com.example.MyTest_Spring.repository.RentalRepository;
import com.example.MyTest_Spring.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public Optional<Reservation> getReservationByParkingId(int parkingId) {
        return reservationRepository.getReservationByParkingId(parkingId);
    }
    public void deleteReservationById(int parkingId,int userId) {
        reservationRepository.deleteReservationById(parkingId,userId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }


    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }


    public void deleteReservation(int reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
