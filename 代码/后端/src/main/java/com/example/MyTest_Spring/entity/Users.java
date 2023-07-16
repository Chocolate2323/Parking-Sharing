package com.example.MyTest_Spring.entity;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
@Table(name = "Users")
public class Users
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private int User_ID;

    @Column(name = "User_Name")
    private String User_Name;

    @Column(name = "Password")
    private String Password;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Phone")
    private String Phone;

    @Column(name = "Score")
    private int Score;

    @Column(name = "Admin")
    private int Admin;

// 添加访问器方法
// ...

    // 构造函数
    public Users() {
    }

    public Users(int User_ID, String User_Name, String Password, String Email, String Phone, int Score,int Admin) {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
        this.Email = Email;
        this.Phone = Phone;
        this.Score = Score;
        this.Admin = Admin;
    }
    public int getUserId() {
        return User_ID;
    }

    public void setUserId(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getUserName() {
        return User_Name;
    }

    public void setUserName(String User_Name) {
        this.User_Name = User_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getAdmin() {
        return Admin;
    }

    public void setAdmin(int Admin) {
        this.Admin = Admin;
    }
}

