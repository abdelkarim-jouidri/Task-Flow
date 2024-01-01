package com.example.taskflow.Entities.DTOs.User;

import com.example.taskflow.Entities.Models.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Builder
    public class createUserDTO {
        @NotNull @NotEmpty
        private String fistName;
        @NotNull @NotEmpty
        private String lastName;
        @NotNull @Size(min = 6)
        private String password;
        @NotNull @NotEmpty
        private Set<Role> authorities;
    }
