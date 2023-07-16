package com.example.MyTest_Spring.controller;

//import com.example.MyTest_Spring.entity.*;
//import com.example.MyTest_Spring.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//@RestController
//public class UserController
//{
//    @Autowired
//    private UserService UserService;
//    @RequestMapping("/findAll")
//    public List<Users> findAll()
//    {
//        return UserService.findAll();
//    }
//    @RequestMapping("/insert")
//    public String insertData(int User_ID,String User_Name,String Password,String Email,String Phone,int Score)
//    {
//        UserService.insertData(1000,"test","123456",
//                "123456@gmail.com","18371534848",100);
//        return "insert successfully";
//    }
//    @RequestMapping("/delete")
//    public String deleteData(){
//        UserService.deleteData();
//        return "delete successfully";
//    }
//
//}

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.MyTest_Spring.entity.Users;
import com.example.MyTest_Spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String User_Name = request.get("User_Name");
        String Password = request.get("Password");

        // 根据用户名查询用户信息
        Optional<Users> optionalUser = userService.getUserByUsername(User_Name);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (user.getPassword().equals(Password)) {
                return ResponseEntity.ok("success");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failure");
    }
    @PostMapping("/user/details")
    public ResponseEntity<Users> getUserDetails(@RequestBody Map<String, String> request) {
        String User_Name = request.get("User_Name");
        String Password = request.get("Password");
        Optional<Users> optionalUser = userService.getUserByUsername(User_Name);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getPassword().equals(Password)) {
                // 构建用户详细信息对象
                Users userDetails = new Users();
                userDetails.setUserId(user.getUserId());
                userDetails.setUserName(user.getUserName());
                userDetails.setPassword(user.getPassword());
                userDetails.setEmail(user.getEmail());
                userDetails.setPhone(user.getPhone());
                userDetails.setScore(user.getScore());
                userDetails.setAdmin(user.getAdmin());

                return ResponseEntity.ok(userDetails);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/userList")
    public List<Users> getUserList() {
        return userService.getAllUsers();
    }

    @PostMapping("/maxUserId")
    public int getMaxUserId() {
        return userService.getMaxUserId();
    }

    @PostMapping("/parking/getname")
    public String getUserName(@RequestBody Map<String, String> request){
        int User_ID = Integer.parseInt(request.get("User_ID"));
        Optional<Users> optionalUser = userService.getUserById(User_ID);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getUserName();
        }
        return "";
    }
    @PostMapping("/userUpdate")
    public ResponseEntity<Users> updateUser(@RequestBody Map<String, String> request) {
        String User_ID = request.get("User_ID");
        String User_Name = request.get("User_Name");
        String Password = request.get("Password");
        String Email = request.get("Email");
        String Phone = request.get("Phone");
        String Score = request.get("Score");
        String Admin = request.get("Admin");
        Optional<Users> optionalUser = getUserById(Integer.parseInt(User_ID));
        if (optionalUser.isPresent()) {
            Users existingUser = optionalUser.get();
            // 更新用户信息
            existingUser.setUserName(User_Name);
            existingUser.setPassword(Password);
            existingUser.setEmail(Email);
            existingUser.setPhone(Phone);
            existingUser.setScore(Integer.parseInt(Score));
            existingUser.setAdmin(Integer.parseInt(Admin));
            // 保存到数据库
            userService.saveUser(existingUser);

            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<Users> addUser(@RequestBody Map<String, String> request) {
        int User_ID = Integer.parseInt(request.get("User_ID"));
        String User_Name = request.get("User_Name");
        String Password = request.get("Password");
        String Email = request.get("Email");
        String Phone = request.get("Phone");
        int Score = Integer.parseInt(request.get("Score"));
        int Admin = Integer.parseInt(request.get("Admin"));
        Users newUser = new Users(User_ID,User_Name,Password,Email,Phone,Score,Admin);
        // 保存用户到数据库
        userService.saveUser(newUser);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/deleteuser")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> request) {
        int User_ID = Integer.parseInt(request.get("User_ID"));
        userService.deleteUserById(User_ID);
        return ResponseEntity.ok("用户删除成功");
    }

    @PostMapping("/userList/searchname")
    public List<Users> searchName(@RequestBody Map<String, String> request) {
        String User_Name = request.get("User_Name");
        return userService.findByUserNameContaining(User_Name);
    }

    @PostMapping("/userList/searchid")
    public ResponseEntity<Users> searchID(@RequestBody Map<String, String> request) {
        int User_ID = Integer.parseInt(request.get("User_ID"));
        Optional<Users> optionalUser = userService.getUserById(User_ID);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            // 构建用户详细信息对象
            Users userDetails = new Users();
            userDetails.setUserId(user.getUserId());
            userDetails.setUserName(user.getUserName());
            userDetails.setPassword(user.getPassword());
            userDetails.setEmail(user.getEmail());
            userDetails.setPhone(user.getPhone());
            userDetails.setScore(user.getScore());
            userDetails.setAdmin(user.getAdmin());

            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.noContent().build();
    }


    // 其他方法...
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    public Optional<Users> getUserById(int id) {
        return userService.getUserById(id);
    }

}
