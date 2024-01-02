package com.example.taskflow.Entities.DTOs.User;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter @NoArgsConstructor @AllArgsConstructor
@Builder
public class viewUserDTO {
    private UUID id;
    private String fistName;
    private String lastName;
    private String password;
    private int dailyChangeTokens;
    private int monthlyDeletionTokens;
    private LocalDateTime lastChangeTokenDate;

    private LocalDateTime lastDeletionTokenDate;
}
