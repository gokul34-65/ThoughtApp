package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Follow;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.repository.FollowRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    Util util;


    public ResponseEntity<String> addFollower(String username, HttpServletRequest request){

        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>("User not found to follow", HttpStatus.NOT_FOUND);
        }
        String follower = util.getUsernameFromRequest(request);
        for(Follow follow : followRepository.findAll()){
            if(follow.getFollower().equals(follower) &&  follow.getFollowing().equals(username)){
                    return new  ResponseEntity<>("already following!", HttpStatus.CONFLICT);
            }
        }
        Follow follow = new Follow();
        follow.setFollowing(username);
        follow.setFollower(follower);
        followRepository.save(follow);
        return new  ResponseEntity<>("Your are now following "+username, HttpStatus.OK);
    }
}
