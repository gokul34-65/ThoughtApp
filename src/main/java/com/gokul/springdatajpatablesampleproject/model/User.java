package com.gokul.springdatajpatablesampleproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private String username;
    @NotEmpty(message = "do not leave password empty")
    private String password;
    @NotEmpty(message = "do not leave displayName empty")
    private String displayName;

    private String bio;
    private String location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    public void addRole(Role role){
        this.roles.add(role);
    }
}
