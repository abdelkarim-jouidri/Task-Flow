package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.Enums.TokenType;
import com.example.taskflow.Entities.Models.*;
import com.example.taskflow.Repositories.RoleRepository;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Repositories.TokenRepository;
import com.example.taskflow.Repositories.UserRepository;
import com.example.taskflow.Services.TaskService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {


    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private TaskRepository taskRepository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private  TaskService taskService;


    @Test
    void addTask() {
    }

    @Test
    void markTaskAsDone() {
    }

    @Test
    void deleteTask_ManagerDeletesTask_Success() {

        UUID userId = UUID.randomUUID();
        int taskId = 1;
        Role MANAGER = new Role(1, "MANAGER");
        Role USER = new Role(2, "USER");
        Set<Role> authorities_manager = new HashSet<>();
        Set<Role> authorities_user = new HashSet<>();
        authorities_user.add(USER);
        authorities_manager.add(MANAGER);
        User manager = new User();
        manager.setId(userId);
        manager.setAuthorities(authorities_manager);

        User user = User.builder().
                authorities(authorities_user).
                id(UUID.randomUUID()).
                fistName("USER").
                lastName("USER").
                monthlyDeletionTokens(1).
                build();
        Tag tag1 = new Tag(1,"TAG 1");
        Tag tag2 = new Tag(1,"TAG 2");
        Tag tag3 = new Tag(1,"TAG 3");

        Set<Tag> tags = new HashSet<>(List.of(tag1, tag2, tag3));

        Task task = new Task();
        task.setId(taskId);
        task.setAssignedTo(user);
        task.setAssignedBy(manager);
        task.setTags(tags);

        Token token = Token.builder().
                task(task).
                type(TokenType.DELETE).
                isConsumed(false).
                user(user).
                createdAt(LocalDateTime.now()).
                build();

        when(userRepository.findUserWithRoleAdmin(userId)).thenReturn(Optional.of(manager));
        when(userRepository.findById(userId)).thenReturn(Optional.of(manager));
        when(tokenRepository.findById(taskId)).thenReturn(Optional.of(token));

        // Act
        assertDoesNotThrow(() -> taskService.deleteTaskForTokenDeletionRequest(userId, taskId));

        // Assert
        verify(tokenRepository,times(1)).save(token);
        verify(taskRepository, times(1)).delete(task);
        assertEquals(0, manager.getMonthlyDeletionTokens());  // Assert that the token is consumed
    }

//    @Test
//    void updateTaskStatus_shouldMarkOverdueTasksAsNotDone() {
//        Task task1 = new Task();
//        task1.setId(1);
//        Task task2 = new Task();
//        task2.setId(2);
//        List<Task> overdueTasks = new ArrayList<>(List.of(task2, task1));
//        when(taskRepository.findOverdueTasks()).thenReturn(overdueTasks);
//        Mockito.verify(taskRepository, Mockito.times(2)).save(Mockito.any(Task.class));
//
//
//    }
}