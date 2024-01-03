package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.DTOs.Task.TaskDTO;
import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Entities.Enums.TokenType;
import com.example.taskflow.Entities.Models.*;
import com.example.taskflow.Exceptions.*;
import com.example.taskflow.Mappings.TaskMapper;
import com.example.taskflow.Repositories.*;
import com.example.taskflow.Services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    private final TokenRepository tokenRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;
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
        if(taskDTO.getAssignedTo().getId()!=null){
            Optional<User> assignedToUser = userRepository.findById(taskDTO.getAssignedTo().getId());
            if(assignedToUser.isEmpty()){
                throw new NoSuchElementException("No such assigned to user for this user id");
            }
            if(!assignedToUser.get().getId().equals(assignedByUser.get().getId()) ){
                if(userRepository.findUserWithRoleAdmin(assignedByUser.get().getId())==null){
                    throw new NonAdminUserCannotAssignATaskException("Only Managers can assign task to users    ");
                }
            }
        }else {
            taskDTO.setAssignedTo(null);
        }

        Task task = TaskMapper.INSTANCE.taskDTOtoTask(taskDTO);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTasked = taskRepository.save(task);
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
    public void deleteTaskForTokenDeletionRequest(UUID managerId, Integer tokenId) {
        userRepository.
            findById(managerId).
            orElseThrow(()->new EntityNotFoundException("there is no user with id = "+managerId));
        userRepository.
                findUserWithRoleAdmin(managerId).
                orElseThrow(()->new UnauthorizedAccessException("Only managers has the authority for this action"));

        Token token = tokenRepository.
                findById(tokenId).
                orElseThrow(() -> new EntityNotFoundException("There is no token with id = " + tokenId));
        if(token.isConsumed()){
            throw new TokenRequestAlreadyConsumedException("Token already consumed");
        }
        if(!token.getType().equals(TokenType.DELETE)) {
            throw new IncorrectTokenTypeException("you can't make a change with token of type Change");
        }
        token.setConsumed(true);
        Task task = token.getTask();
        token.setTask(null);
        tokenRepository.save(token);
        task.getTags().clear();
        taskRepository.delete(task);

    }

    @Override
    public void changeTask(UUID managerId, Integer tokenId) {
        userRepository.
                findById(managerId).
                orElseThrow(()->new EntityNotFoundException("there is no user with id = "+managerId));
        userRepository.
                findUserWithRoleAdmin(managerId).
                orElseThrow(()->new UnauthorizedAccessException("Only managers has the authority for this action"));

        Token token = tokenRepository.
                findById(tokenId).
                orElseThrow(() -> new EntityNotFoundException("There is no token with id = " + tokenId));
        if(token.isConsumed()){
            throw new TokenRequestAlreadyConsumedException("Token already consumed");
        }
        if(!token.getType().equals(TokenType.CHANGE)) {
            throw new IncorrectTokenTypeException("you can't make a change with token of type Deletion");
        }
        User ownerOfToken = token.getUser();
        Task taskAssociatedToToken = token.getTask();
        if (token.getTask().isReplaced()){
            throw new CannotReplaceTaskException("This task has already been replaced before");
        }
        Task targetTask = taskRepository.
                findReplaceableNonAssignedTaskWithDefaultMaxResults().
                orElseThrow(() -> new EntityNotFoundException("No available non assigned task to change to"));
        Optional<User> targetAssigneeUser = userRepository.findUserWithMinNumberOfTasks();
        if(targetAssigneeUser.isEmpty()) throw new NoSuchElementException("No available User To change the assignment of the task to");
        targetTask.setAssignedTo(ownerOfToken);
        taskAssociatedToToken.setAssignedTo(targetAssigneeUser.get());
        token.setConsumed(true);
        taskRepository.saveAll(new ArrayList<>(List.of(targetTask, taskAssociatedToToken)));
        tokenRepository.save(token);

    }

    @Override
    public void deleteTask(UUID managerId, Integer taskId) {
        User user = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("User with userId=" + managerId + " not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with taskId=" + taskId + " not found"));

        Optional<Role> managerRole = roleRepository.findByAuthority("MANAGER");
        if (managerRole.isEmpty()) throw new NoSuchElementException("There is no Role that has MANAGER authority");

        if (!user.getAuthorities().contains(managerRole.get())) {
            throw new UnauthorizedAccessException("Only managers can delete tasks.");
        }

        taskRepository.delete(task);

    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::taskToTaskDTO).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void updateTaskStatus() {
        List<Task> overdueTasks = taskRepository.findOverdueTasks();

        for (Task task : overdueTasks) {
            task.setTaskStatus(TaskStatus.NOT_DONE);
            taskRepository.save(task);
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void grantDoubleChangeTokens() {
        List<Token> nonConsumedTokens = tokenRepository.getTokensByConsumedIsFalse();

        for (Token token : nonConsumedTokens) {
            LocalDateTime createdAt = token.getCreatedAt();

            if (createdAt != null &&
                    Duration.between(createdAt, LocalDateTime.now()).toHours() >= 12) {
                User user = token.getUser();
                user.setDailyChangeTokens(4);
                userRepository.save(user);
            }
        }
    }

}
