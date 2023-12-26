package com.example.taskflow.Entities.DTOs.Task;

import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Validations.ValidDueDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TaskDTO {
    @NotEmpty
    private String description;
    @Future
    private LocalDate createdAt;
    @ValidDueDate
    private LocalDate dueDate;

    private TaskStatus taskStatus;
}
