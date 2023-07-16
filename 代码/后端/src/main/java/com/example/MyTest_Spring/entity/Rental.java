package com.example.MyTest_Spring.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Rental")
public class Rental
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rental_ID")
    private int Rental_ID;
    @Column(name = "Parking_ID")
    private int Parking_ID;
    @Column(name = "User_ID")
    private int User_ID;
    @Column(name = "Price")
    private int Price;
    @Column(name = "Start_Time")
    private Time Start_Time;
    @Column(name = "End_Time")
    private Time End_Time;

    public Rental() {
    }

    public Rental( int Parking_ID, int User_ID, int Price, Time Start_Time, Time End_Time) {
        this.Parking_ID = Parking_ID;
        this.User_ID = User_ID;
        this.Price = Price;
        this.Start_Time = Start_Time;
        this.End_Time = End_Time;
    }

    public int getRentalID() {
        return Rental_ID;
    }

    public void setRentalID(int Rental_ID) {
        this.Rental_ID = Rental_ID;
    }

    public int getParkingID() {
        return Parking_ID;
    }

    public void setParkingID(int Parking_ID) {
        this.Parking_ID = Parking_ID;
    }

    public int getUserID() {
        return User_ID;
    }

    public void setUserID(int User_ID) {
        this.User_ID = User_ID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public Time getStartTime() {
        return Start_Time;
    }

    public void setStartTime(Time Start_Time) {
        this.Start_Time = Start_Time;
    }

    public Time getEndTime() {
        return End_Time;
    }

    public void setEndTime(Time End_Time) {
        this.End_Time = End_Time;
    }
}
