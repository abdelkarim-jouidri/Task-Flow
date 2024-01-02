package com.example.taskflow.Entities.DTOs.Task;

import com.example.taskflow.Entities.DTOs.Tag.TagDTO;
import com.example.taskflow.Entities.DTOs.User.viewUserDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Validations.ValidStartDate;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class TaskDTO {
    @NotEmpty
    private String description;
    @ValidStartDate
    private LocalDateTime startDate;;
    private LocalDateTime dueDate;
    private TaskStatus taskStatus;
    @Size(min = 3 , message = "you should enter a minimum of 3 tags") @NotNull
    private Set<TagDTO> tags;
    @NotNull
    private viewUserDTO assignedBy;
    @NotNull
    private viewUserDTO assignedTo;

}
