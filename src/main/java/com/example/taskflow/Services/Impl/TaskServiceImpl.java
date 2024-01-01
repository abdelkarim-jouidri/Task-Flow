package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Entities.Models.Tag;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Exceptions.CannotMarkATaskAsDoneException;
import com.example.taskflow.Exceptions.DueDateIsInThePastException;
import com.example.taskflow.Exceptions.InvalidDueDateException;
import com.example.taskflow.Exceptions.NonAdminUserCannotAssignATaskException;
import com.example.taskflow.Mappings.TaskMapper;
import com.example.taskflow.Repositories.TagRepository;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service("TaskServiceImpl")
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        LocalDate startDate = taskDTO.getStartDate();
        LocalDate dueDate = taskDTO.getDueDate();
        if(!dueDate.isAfter(startDate)){
            throw new InvalidDueDateException("Due date should be after the starting date");
        }
        List<Integer> tagsId = taskDTO.getTags().stream().map(tag -> tag.getId()).collect(Collectors.toList());
        for (Integer id : tagsId){
            Optional<Tag> optionalTag = tagRepository.findById(id);
            if(optionalTag.isEmpty()){
                throw new NoSuchElementException("no such tag for this id");
            }
        }
        if(userRepository.findById(taskDTO.getAssignedBy().getId()).isEmpty()){
            throw new NoSuchElementException("No such assigned by user for this user id");
        }
        if(userRepository.findUserWithRoleAdmin(taskDTO.getAssignedBy().getId())==null){
            throw new NonAdminUserCannotAssignATaskException("Non admin user cannot assign a task");
        }
        if(userRepository.findById(taskDTO.getAssignedTo().getId()).isEmpty()){
            throw new NoSuchElementException("No such assigned to user for this user id");
        }
        Task task = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
        Task savedTasked = taskRepository.save(task);

        return TaskMapper.INSTANCE.taskToTaskDTO(savedTasked);
    }

    @Override
    public TaskDTO markTaskAsDone(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if(task.getDueDate().isBefore(LocalDate.now())){
            task.setTaskStatus(TaskStatus.DONE);
            Task savedTask = taskRepository.save(task);
            return TaskMapper.INSTANCE.taskToTaskDTO(savedTask);
        }
        throw new DueDateIsInThePastException();
    }
}
