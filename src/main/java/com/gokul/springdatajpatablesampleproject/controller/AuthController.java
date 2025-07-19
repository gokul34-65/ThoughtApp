package com.gokul.springdatajpatablesampleproject.controller;

import com.gokul.springdatajpatablesampleproject.model.LoginTemplate;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.service.JwtUtil;
import com.gokul.springdatajpatablesampleproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @PostMapping("register")
    public ResponseEntity<String> addUser(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }
        return userService.addUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginTemplate login){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        if(authentication.isAuthenticated()){
            User user = userService.getByUserName(login.getUsername());
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
