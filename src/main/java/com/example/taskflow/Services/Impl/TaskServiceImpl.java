package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Entities.Models.Role;
import com.example.taskflow.Entities.Models.Tag;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Exceptions.*;
import com.example.taskflow.Mappings.TaskMapper;
import com.example.taskflow.Repositories.RoleRepository;
import com.example.taskflow.Repositories.TagRepository;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("TaskServiceImpl")
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        LocalDateTime startDate = taskDTO.getStartDate();
        LocalDateTime dueDate = taskDTO.getDueDate();
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
        Optional<User> assignedByUser = userRepository.findById(taskDTO.getAssignedBy().getId());
        if(assignedByUser.isEmpty()){
            throw new NoSuchElementException("No such assigned by user for this user id");
        }
        Optional<User> assignedToUser = userRepository.findById(taskDTO.getAssignedTo().getId());
        if(assignedToUser.isEmpty()){
            throw new NoSuchElementException("No such assigned to user for this user id");
        }

        if(!assignedToUser.get().getId().equals(assignedByUser.get().getId()) ){
            if(userRepository.findUserWithRoleAdmin(assignedByUser.get().getId())==null){
                throw new NonAdminUserCannotAssignATaskException("Only Managers can assign task to users    ");
            }
        }
        Task task = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
        Task savedTasked = taskRepository.save(task);
        savedTasked.setCreatedAt(LocalDateTime.now());
        return TaskMapper.INSTANCE.taskToTaskDTO(savedTasked);
    }

    @Override
    public TaskDTO markTaskAsDone(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if(task.getDueDate().isBefore(LocalDateTime.now())){
            task.setTaskStatus(TaskStatus.DONE);
            Task savedTask = taskRepository.save(task);
            return TaskMapper.INSTANCE.taskToTaskDTO(savedTask);
        }
        throw new DueDateIsInThePastException();
    }

    @Override
    public void deleteTask(UUID userId, Integer taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with userId=" + userId + " not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with taskId=" + taskId + " not found"));

        Optional<Role> managerRole = roleRepository.findByAuthority("MANAGER");
        if (managerRole.isEmpty()) throw new NoSuchElementException("The is no Role that has MANAGER authority");

        if (!user.getAuthorities().contains(managerRole.get())) {
            throw new UnauthorizedAccessException("Only managers can delete tasks.");
        }

        if (task.getAssignedTo().getId().equals(userId)) {
            taskRepository.delete(task);
        } else {
            if (user.getMonthlyDeletionTokens() > 0) {
                taskRepository.delete(task);
                user.setMonthlyDeletionTokens(user.getMonthlyDeletionTokens() - 1);
                userRepository.save(user);
            } else {
                throw new NoTokenCreditException("Monthly deletion token limit exceeded.");
            }
        }
    }


}
