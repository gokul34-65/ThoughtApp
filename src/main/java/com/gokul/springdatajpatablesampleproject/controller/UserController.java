package com.gokul.springdatajpatablesampleproject.controller;
import com.gokul.springdatajpatablesampleproject.model.LoginTemplate;
import com.gokul.springdatajpatablesampleproject.model.Role;
import com.gokul.springdatajpatablesampleproject.model.User;
import com.gokul.springdatajpatablesampleproject.model.UserDTO;
import com.gokul.springdatajpatablesampleproject.service.JwtUtil;
import com.gokul.springdatajpatablesampleproject.service.RoleService;
import com.gokul.springdatajpatablesampleproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Autowired
    JwtUtil  jwtUtil;

    @PostMapping("addRole")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

    @GetMapping("{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getUserDTOByUsername(username), HttpStatus.OK);
    }

    @GetMapping("me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request){
        return new ResponseEntity<>(userService.getCurrentUserDTO(request), HttpStatus.OK);
    }
}
