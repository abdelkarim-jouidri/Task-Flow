package com.example.taskflow.Entities.DTOs.Task;

import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Validations.ValidDueDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDTO {
    private String description;
    private LocalDate createdAt;
    @ValidDueDate
    private LocalDate dueDate;

    private TaskStatus taskStatus;
}
