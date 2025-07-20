package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.repository.PostRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Util util;

    public ResponseEntity<String> addPost(HttpServletRequest request, Post post){
        String username = util.getUsernameFromRequest(request);
        post.setUsername(username);//manually setting the username
        postRepository.save(post);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    public List<Post> getAllPostDTOs() {
        List<Post> postDTOs = new ArrayList<>();
        for(Post post : postRepository.findAll()){
            postDTOs.add(new Post(post.getId(),post.getContent(),post.getUsername()));
        }
         return postDTOs;
    }
}
