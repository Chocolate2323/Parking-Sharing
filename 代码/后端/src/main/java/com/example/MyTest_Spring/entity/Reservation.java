package com.example.MyTest_Spring.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Reservation")
public class Reservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reservation_ID")
    private int Reservation_ID;
    @Column(name = "Parking_ID")
    private int Parking_ID;
    @Column(name = "User_ID")
    private int User_ID;
    @Column(name = "Start_Time")
    private Time Start_Time;
    @Column(name = "End_Time")
    private Time End_Time;

    public Reservation() {
    }

    public Reservation( int Parking_ID, int User_ID, Time Start_Time, Time End_Time) {
        this.Parking_ID = Parking_ID;
        this.User_ID = User_ID;
        this.Start_Time = Start_Time;
        this.End_Time = End_Time;
    }

    public int getReservationID() {
        return Reservation_ID;
    }

    public void setReservationID(int Reservation_ID) {
        this.Reservation_ID = Reservation_ID;
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

