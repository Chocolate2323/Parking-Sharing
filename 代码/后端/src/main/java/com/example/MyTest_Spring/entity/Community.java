package com.example.MyTest_Spring.entity;

import javax.persistence.*;

@Entity
@Table(name = "Community")
public class Community
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Community_ID")
    private int Community_ID;
    @Column(name = "Community_Name")
    private String Community_Name;
    @Column(name = "Position")
    private String Position;
    @Column(name = "Longitude")
    private float Longitude;
    @Column(name = "Latitude")
    private float Latitude;

    public Community() {
    }

    public Community(String Community_Name, String Position, float Longitude, float Latitude) {
        this.Community_Name = Community_Name;
        this.Position = Position;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }

    public int getCommunityID() {
        return Community_ID;
    }

    public void setCommunityID(int Community_ID) {
        this.Community_ID = Community_ID;
    }

    public String getCommunityName() {
        return Community_Name;
    }

    public void setCommunityName(String Community_Name) {
        this.Community_Name = Community_Name;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float Longitude) {
        this.Longitude = Longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float Latitude) {
        this.Latitude = Latitude;
    }
}
