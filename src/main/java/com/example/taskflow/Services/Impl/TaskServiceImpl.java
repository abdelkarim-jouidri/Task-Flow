package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Mappings.TaskMapper;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("TaskServiceImpl")
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
        Task savedTasked = taskRepository.save(task);
        return TaskMapper.INSTANCE.taskToTaskDTO(savedTasked);
    }
}
