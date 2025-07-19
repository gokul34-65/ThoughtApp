package com.gokul.springdatajpatablesampleproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class UserDTO {

    private String username;
    private String displayName;
    private String bio;
    private String location;

    public UserDTO() {
    }

    public UserDTO(String username, String displayName, String bio, String location) {
        this.displayName = displayName;
        this.bio = bio;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
