package com.gokul.springdatajpatablesampleproject.service;

import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.model.UserDTO;
import com.gokul.springdatajpatablesampleproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Util {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    public UserDTO getUserDTOByUsername(String username){
        UserDTO userDTO = new UserDTO();
        User user = userRepository.findByUsername(username);
        userDTO.setDisplayName(user.getDisplayName());
        userDTO.setBio(user.getBio());
        userDTO.setLocation(user.getLocation());
        return userDTO;
    }

    public UserDTO getCurrentUserDTO(HttpServletRequest request) {
        String username = getUsernameFromRequest(request);
        return getUserDTOByUsername(username);
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(7);
        String username = jwtUtil.extractUserName(token);
        return username;
    }
}
