package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Follow;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.model.UserDTO;
import com.gokul.springdatajpatablesampleproject.repository.FollowRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> deleteFollower(String username, HttpServletRequest request) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseEntity<>("User not found to unfollow", HttpStatus.NOT_FOUND);
        }
        String follower = util.getUsernameFromRequest(request);
        for(Follow follow : followRepository.findAll()){
            if(follow.getFollower().equals(follower) &&  follow.getFollowing().equals(username)){
                followRepository.delete(follow);
                return new  ResponseEntity<>("unfollow successfull", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Already not following!", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<UserDTO>> getFollowers(HttpServletRequest request) {
        List<Follow> follows = followRepository.findByFollowing(util.getUsernameFromRequest(request));
        List<UserDTO> userDTOs = new ArrayList<>();
        for(Follow follow : follows){
            User follower = userRepository.findByUsername(follow.getFollower());
            UserDTO userDTO = new UserDTO();
            userDTO.setDisplayName(follower.getDisplayName());
            userDTO.setBio(follower.getBio());
            userDTO.setLocation(follower.getLocation());
            userDTOs.add(userDTO);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }
    public ResponseEntity<List<UserDTO>> getFollowing(HttpServletRequest request) {
        List<Follow> follows = followRepository.findByFollower(util.getUsernameFromRequest(request));
        List<UserDTO> userDTOs = new ArrayList<>();
        for(Follow follow : follows){
            User follower = userRepository.findByUsername(follow.getFollowing());
            UserDTO userDTO = new UserDTO();
            userDTO.setDisplayName(follower.getDisplayName());
            userDTO.setBio(follower.getBio());
            userDTO.setLocation(follower.getLocation());
            userDTOs.add(userDTO);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }


    public ResponseEntity<Boolean> isFollowing(HttpServletRequest request, String username) {
        if(!util.userExists(username)){
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
        String currentUsername = util.getUsernameFromRequest(request);
        for(Follow follow : followRepository.findAll()){
            if(follow.getFollower().equals(currentUsername) &&  follow.getFollowing().equals(username)){
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(false,HttpStatus.OK);
    }


}
