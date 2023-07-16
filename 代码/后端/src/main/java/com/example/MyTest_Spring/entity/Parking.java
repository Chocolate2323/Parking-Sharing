package com.example.MyTest_Spring.entity;


import javax.persistence.*;

@Entity
@Table(name = "Parking")
public class Parking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Parking_ID")
    private int Parking_ID;
    @Column(name = "Parking_Number")
    private String Parking_Number;
    @Column(name = "Community_ID")
    private int Community_ID;
    @Column(name = "Area")
    private float Area;
    @Column(name = "States")
    private String States;
    @Column(name = "Price")
    private int Price;

    public Parking() {
    }

    public Parking( String Parking_Number, int Community_ID, float Area, String States, int Price) {
        this.Parking_Number = Parking_Number;
        this.Community_ID = Community_ID;
        this.Area = Area;
        this.States = States;
        this.Price = Price;
    }

    public int getParkingID() {
        return Parking_ID;
    }

    public void setParkingID(int Parking_ID) {
        this.Parking_ID = Parking_ID;
    }

    public String getParkingNumber() {
        return Parking_Number;
    }

    public void setParkingNumber(String Parking_Number) {
        this.Parking_Number = Parking_Number;
    }

    public int getCommunityID() {
        return Community_ID;
    }

    public void setCommunityID(int Community_ID) {
        this.Community_ID = Community_ID;
    }

    public float getArea() {
        return Area;
    }

    public void setArea(float Area) {
        this.Area = Area;
    }

    public String getStates() {
        return States;
    }

    public void setStates(String States) {
        this.States = States;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }
}
