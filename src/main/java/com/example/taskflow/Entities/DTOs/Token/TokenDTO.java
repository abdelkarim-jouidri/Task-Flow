package com.example.taskflow.Entities.DTOs.Token;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Enums.TokenType;
import com.example.taskflow.Entities.Models.Task;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class TokenDTO {
    private Long id;
    private viewUserDTO user;
    private LocalDateTime createdAt;
    private TokenType type;
    private TaskDTO task;
    private boolean isConsumed;


}
