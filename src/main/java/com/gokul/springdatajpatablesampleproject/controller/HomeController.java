package com.gokul.springdatajpatablesampleproject.controller;


import com.gokul.springdatajpatablesampleproject.model.Role;
import com.gokul.springdatajpatablesampleproject.service.RoleService;
import com.gokul.springdatajpatablesampleproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }
}
