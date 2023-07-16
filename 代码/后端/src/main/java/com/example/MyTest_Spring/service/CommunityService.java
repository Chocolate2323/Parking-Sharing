package com.example.MyTest_Spring.service;

import java.util.List;
import java.util.Optional;


import com.example.MyTest_Spring.entity.Community;
import com.example.MyTest_Spring.entity.Parking;
import com.example.MyTest_Spring.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }


    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public Community createCommunity(Community community) {
        return communityRepository.save(community);
    }

    public Community updateCommunity(Community community) {
        return communityRepository.save(community);
    }

    public void deleteCommunity(int communityId) {
        communityRepository.deleteById(communityId);
    }

    public Optional<Community> getCommunityById(String communityId) {
        return communityRepository.findById(Integer.parseInt(communityId));
    }

    public void saveCommunity(Community community) {
        communityRepository.save(community);
    }
}
