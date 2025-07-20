package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.model.UserDTO;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    Util util;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<String> addUser(User user) {
        if(userRepository.findById(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        //check to see if roles[] is empty, if empty assign a default role(USER)
        if(user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(roleService.getUserRole());
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added");
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public User getByUserName(String username){
        return  userRepository.findByUsername(username);
    }



    public ResponseEntity<String> updateCurrentUser(HttpServletRequest request, UserDTO userDTO) {
        String username = util.getUsernameFromRequest(request);
        User user = userRepository.findByUsername(username);
        user.setBio(userDTO.getBio());
        user.setLocation(userDTO.getLocation());
        user.setDisplayName(userDTO.getDisplayName());
        userRepository.save(user);

        return new  ResponseEntity<>("updation successful", HttpStatus.OK);
    }
}
