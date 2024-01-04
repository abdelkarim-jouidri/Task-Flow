package com.example.taskflow.Entities.DTOs.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class deleteTaskDTO {
    @NotNull
    private UUID userId;
    @NotNull
    private Integer taskId;
}
