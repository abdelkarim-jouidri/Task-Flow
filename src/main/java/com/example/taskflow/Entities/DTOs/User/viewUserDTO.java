package com.example.taskflow.Entities.DTOs.User;

import lombok.*;

import java.util.UUID;

@Setter
@Getter @NoArgsConstructor @AllArgsConstructor
@Builder
public class viewUserDTO {
    private UUID id;
    private String fistName;
    private String lastName;
    private String password;
}
