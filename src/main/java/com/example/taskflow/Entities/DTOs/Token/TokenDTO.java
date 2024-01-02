package com.example.taskflow.Entities.DTOs.Token;

import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Enums.TokenType;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class TokenDTO {
    private viewUserDTO user;
    private LocalDateTime createdAt;

    private TokenType type;


}
