package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;
import com.example.MyTest_Spring.repository.UserRepository;
import com.example.MyTest_Spring.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(int id) {
        return userRepository.findById(id);
    }

    public void saveUser(Users user) {
        userRepository.save(user);
    }
    public void addUser(int User_ID,String User_Name,String Password,String Email,String Phone,int Score,int Admin) {
        userRepository.add(User_ID,User_Name,Password,Email,Phone,Score,Admin);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public Optional<Users> getUserByUsername(String User_Name) {
        return userRepository.findByUserName(User_Name);
    }

    public int getMaxUserId(){return userRepository.getMaxUserId();}

    public List<Users> findByUserNameContaining(String User_Name) {
        return userRepository.findByUserNameContaining(User_Name);
    }


}
