package com.example.taskflow.Services;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Exceptions.DueDateIsInThePastException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface TaskService {
    TaskDTO addTask(TaskDTO taskDTO);
    TaskDTO markTaskAsDone(Integer taskId) throws DueDateIsInThePastException;
    void deleteTaskForTokenDeletionRequest(UUID userId, Integer taskId);

    void changeTask(UUID managerId, Integer tokenId);
    void deleteTask(UUID managerId, Integer taskId);

    List<TaskDTO> getAllTasks();

}
