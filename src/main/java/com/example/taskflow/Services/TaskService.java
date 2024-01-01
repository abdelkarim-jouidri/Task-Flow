package com.example.taskflow.Services;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Exceptions.DueDateIsInThePastException;
import org.springframework.stereotype.Component;

@Component
public interface TaskService {
    TaskDTO addTask(TaskDTO taskDTO);
    TaskDTO markTaskAsDone(Integer taskId) throws DueDateIsInThePastException;

}
