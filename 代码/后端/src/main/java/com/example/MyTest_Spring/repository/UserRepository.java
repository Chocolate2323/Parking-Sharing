package com.example.MyTest_Spring.repository;

import com.example.MyTest_Spring.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    //    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=My_Test_Car;ssl=false;encrypt=true;trustServerCertificate=true";
////    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=My_Test_Car;encrypt=false;trustServerCertificate=true;sslProtocol=disable;ssl=false";
//
//    private final String username = "Chocolate";
//    private final String password = "20031015";
//
//    public List<Users> getAllUsers() {
//        List<Users> users = new ArrayList<>();
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
//
//            while (resultSet.next()) {
//                Users user = new Users();
//                user.setUserId(resultSet.getInt("User_ID"));
//                user.setUserName(resultSet.getString("User_Name"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setEmail(resultSet.getString("Email"));
//                user.setPhone(resultSet.getString("Phone"));
//                user.setScore(resultSet.getInt("Score"));
//                user.setAdmin(resultSet.getInt("Admin"));
//
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return users;
//    }
//
//    public Optional<Users> getUserById(int id) {
//        Users user = null;
//
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE User_ID = ?")) {
//
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                user = new Users();
//                user.setUserId(resultSet.getInt("User_ID"));
//                user.setUserName(resultSet.getString("User_Name"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setEmail(resultSet.getString("Email"));
//                user.setPhone(resultSet.getString("Phone"));
//                user.setScore(resultSet.getInt("Score"));
//                user.setAdmin(resultSet.getInt("Admin"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return Optional.ofNullable(user);
//    }
//
//    public void saveUser(Users user) {
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (User_ID, User_Name, Password, Email, Phone, Score, Admin) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
//            statement.setInt(1, user.getUserId());
//            statement.setString(2, user.getUserName());
//            statement.setString(3, user.getPassword());
//            statement.setString(4, user.getEmail());
//            statement.setString(5, user.getPhone());
//            statement.setInt(6, user.getScore());
//            statement.setInt(7, user.getAdmin());
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteUserById(int id) {
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE User_ID = ?")) {
//
//            statement.setInt(1, id);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public Optional<Users> findByUsername(String username) {
//        Users user = null;
//
//        try (Connection connection = DriverManager.getConnection(url, this.username, password);
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE User_Name = ?")) {
//
//            statement.setString(1, username);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                user = new Users();
//                user.setUserId(resultSet.getInt("User_ID"));
//                user.setUserName(resultSet.getString("User_Name"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setEmail(resultSet.getString("Email"));
//                user.setPhone(resultSet.getString("Phone"));
//                user.setScore(resultSet.getInt("Score"));
//                user.setAdmin(resultSet.getInt("Admin"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return Optional.ofNullable(user);
//    }
    @Query("SELECT u FROM Users u WHERE u.User_Name = :User_Name")
    Optional<Users> findByUserName(@Param("User_Name") String User_Name);

    @Query(value = "SELECT MAX(u.User_ID) FROM Users u")
    int getMaxUserId();

    @Modifying
    @Query(value = "INSERT INTO Users (User_ID, User_Name, Password, Email, Phone, Score, Admin) " +
            "VALUES (:User_ID, :User_Name, :Password, :Email, :Phone, :Score, :Admin)", nativeQuery = true)
    void add(@Param("User_ID") int User_ID,
            @Param("User_Name") String User_Name,
            @Param("Password") String Password,
            @Param("Email") String Email,
            @Param("Phone") String Phone,
            @Param("Score") int Score,
            @Param("Admin") int Admin);


    @Query("SELECT u FROM Users u WHERE u.User_Name LIKE %:User_Name%")
    List<Users> findByUserNameContaining(@Param("User_Name") String User_Name);



}
