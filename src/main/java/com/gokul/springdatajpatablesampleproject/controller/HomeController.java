package com.gokul.springdatajpatablesampleproject.controller;
import com.gokul.springdatajpatablesampleproject.model.Post;
import com.gokul.springdatajpatablesampleproject.service.PostService;
import com.gokul.springdatajpatablesampleproject.service.RoleService;
import com.gokul.springdatajpatablesampleproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @Autowired
    PostService postService;

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPostDTOs(), HttpStatus.OK);
    }
}
