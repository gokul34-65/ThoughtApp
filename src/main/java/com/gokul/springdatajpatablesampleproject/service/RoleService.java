package com.gokul.springdatajpatablesampleproject.service;


import com.gokul.springdatajpatablesampleproject.model.Role;
import com.gokul.springdatajpatablesampleproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;


    public ResponseEntity<String> addRole(Role role){
       if(roleRepository.findById(role.getId()).isPresent()){
           return ResponseEntity.status(HttpStatus.CONFLICT).body("Role already exists");
       }
       roleRepository.save(role);
       return ResponseEntity.status(HttpStatus.CREATED).body("Role added");
    }

    public Set<Role> getUserRole(){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(2).get());
        return roles;
    }
}
