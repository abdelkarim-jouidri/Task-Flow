package com.example.taskflow.Entities.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity @Table(name = "users")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    private UUID id;

    private String fistName;
    private String lastName;
    private String password;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns ={@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

}
