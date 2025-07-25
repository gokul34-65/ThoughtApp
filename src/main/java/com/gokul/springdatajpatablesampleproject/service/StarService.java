package com.gokul.springdatajpatablesampleproject.service;


import com.gokul.springdatajpatablesampleproject.model.Star;
import com.gokul.springdatajpatablesampleproject.repository.PostRepository;
import com.gokul.springdatajpatablesampleproject.repository.StarRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarService {

    @Autowired
    StarRepository starRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    Util util;
    public ResponseEntity<String> addStar(HttpServletRequest request, Long postId) {
        String username = util.getUsernameFromRequest(request);
        if(util.postExists(postId)) {
            List<Star> stars = starRepository.findAll();
            for(Star star : stars) {
                if(star.getUsername().equals(username) && star.getPostId().equals(postId)) {
                    return new  ResponseEntity<>("Already Liked this post",HttpStatus.CONFLICT);
                }
            }
            Star star = new Star();
            star.setUsername(username);
            star.setPostId(postId);
            starRepository.save(star);
            return new ResponseEntity<>("Star added successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("post not found to star", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> unStar(HttpServletRequest request, Long postId) {
        String username = util.getUsernameFromRequest(request);
       List<Star> post_stars =  starRepository.findByPostId(postId);
       for(Star star : post_stars) {
           if(star.getUsername().equals(username)) {
               starRepository.delete(star);
               return new ResponseEntity<>("Star deleted successfully", HttpStatus.OK);
           }
       }
       return new ResponseEntity<>("No star found to delete :(", HttpStatus.NOT_FOUND);
    }
}
