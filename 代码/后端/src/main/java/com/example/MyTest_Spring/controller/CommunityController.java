package com.example.MyTest_Spring.controller;

import com.example.MyTest_Spring.entity.Community;
import com.example.MyTest_Spring.entity.Parking;
import com.example.MyTest_Spring.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/communityList")
    public List<Community> getAllCommunities() {
        return communityService.getAllCommunities();
    }

//    @GetMapping("/{communityId}")
//    public Community getCommunityById(@PathVariable int communityId) {
//        return communityService.getCommunityById(communityId);
//    }

    @PostMapping("/parking/getposition")
    public String searchPosition(@RequestBody Map<String, String> request) {
        String communityId = request.get("Community_ID");
        Optional<Community> optionalCommunity = communityService.getCommunityById(communityId);

        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            return community.getPosition();
        } else {
            return ""; // 或者根据需要返回适当的默认值或错误信息
        }
    }

    @PostMapping("/parking/getcommunityname")
    public String searchName(@RequestBody Map<String, String> request) {
        String communityId = request.get("Community_ID");
        Optional<Community> optionalCommunity = communityService.getCommunityById(communityId);

        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            return community.getCommunityName();
        } else {
            return ""; // 或者根据需要返回适当的默认值或错误信息
        }
    }

    @PostMapping("/parking/getcommunitylongitude")
    public float searchLongitude(@RequestBody Map<String, String> request) {
        String communityId = request.get("Community_ID");
        Optional<Community> optionalCommunity = communityService.getCommunityById(communityId);

        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            return community.getLongitude();
        } else {
            return 0; // 或者根据需要返回适当的默认值或错误信息
        }
    }
    @PostMapping("/parking/getcommunitylatitude")
    public float searchLatitude(@RequestBody Map<String, String> request) {
        String communityId = request.get("Community_ID");
        Optional<Community> optionalCommunity = communityService.getCommunityById(communityId);

        if (optionalCommunity.isPresent()) {
            Community community = optionalCommunity.get();
            return community.getLatitude();
        } else {
            return 0; // 或者根据需要返回适当的默认值或错误信息
        }
    }

    @PostMapping("/community")
    public Community createCommunity(@RequestBody Community community) {
        return communityService.createCommunity(community);
    }

    @PostMapping("/parkingUpdate2")
    public ResponseEntity<Community> updateCommunity(@RequestBody Map<String, String> request) {
        String Community_ID = request.get("Community_ID");
        String Position = request.get("Position");
        String CommunityName = request.get("CommunityName");
        String Longitude = request.get("Longitude");
        String Latitude = request.get("Latitude");
        Optional<Community> optionalCommunity = communityService.getCommunityById(Community_ID);
        if (optionalCommunity.isPresent()) {
            Community existingCommunity = optionalCommunity.get();
            existingCommunity.setPosition(Position);
            existingCommunity.setCommunityName(CommunityName);
            existingCommunity.setLongitude(Float.parseFloat(Longitude));
            existingCommunity.setLatitude(Float.parseFloat(Latitude));
            // 保存到数据库
            communityService.saveCommunity(existingCommunity);

            return ResponseEntity.ok(existingCommunity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addparking2")
    public ResponseEntity<Community> addCommunity(@RequestBody Map<String, String> request) {
        String Position = request.get("Position");
        String CommunityName = request.get("CommunityName");
        float Longitude = Float.parseFloat(request.get("Longitude"));
        float Latitude = Float.parseFloat(request.get("Latitude"));
        Community newCommunity = new Community(CommunityName,Position,Longitude,Latitude);
        // 保存用户到数据库
        communityService.saveCommunity(newCommunity);
        return ResponseEntity.ok(newCommunity);
    }
    @PostMapping("/deletecommunity")
    public ResponseEntity<String> deleteCommunity(@RequestBody Map<String, String> request) {
        int Community_ID = Integer.parseInt(request.get("Community_ID"));
        communityService.deleteCommunity(Community_ID);
        return ResponseEntity.ok("删除成功");
    }
    @DeleteMapping("/{communityId}")
    public void deleteCommunity(@PathVariable int communityId) {
        communityService.deleteCommunity(communityId);
    }

    // 处理其他自定义的API请求...
}
