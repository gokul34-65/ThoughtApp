package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.model.PostDTO;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.repository.PostRepository;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user = userRepository.findById(username).orElse(null);
        if(user == null){
            return new ResponseEntity<>("Cannot find user with the requested name",HttpStatus.BAD_REQUEST);
        }
        post.setUser(user);
        user.getPosts().add(post);
        userRepository.save(user);
        return new ResponseEntity<>("Post added successfully", HttpStatus.OK);
    }

    public List<PostDTO> getAllPostDTOs() {
        List<PostDTO> postDTOs = new ArrayList<>();
        for(Post post : postRepository.findAll()){
            postDTOs.add(new PostDTO(post.getId(),post.getContent(),post.getUser().getUsername()));
        }
         return postDTOs;
    }
}
