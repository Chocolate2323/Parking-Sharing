package com.example.MyTest_Spring.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "Comment")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Comment_ID")
    private int Comment_ID;
    @Column(name = "Rental_ID")
    private int Rental_ID;
    @Column(name = "Parking_ID")
    private int Parking_ID;
    @Column(name = "User_ID")
    private int User_ID;
    @Column(name = "Comments")
    private String Comments;
    @Column(name = "Score")
    private float Score;

    public Comment() {
    }

    public Comment(int Rental_ID, int Parking_ID, int User_ID, String Comments, float Score) {
        this.Rental_ID = Rental_ID;
        this.Parking_ID = Parking_ID;
        this.User_ID = User_ID;
        this.Comments = Comments;
        this.Score = Score;
    }

    public int getCommentID() {
        return Comment_ID;
    }

    public void setCommentID(int Comment_ID) {
        this.Comment_ID = Comment_ID;
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


    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public float getScore() {
        return Score;
    }

    public void setScore(float Score) {
        this.Score = Score;
    }
}
