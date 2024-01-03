package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.Models.Role;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Repositories.RoleRepository;
import com.example.taskflow.Repositories.TaskRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TaskServiceImplTest {


    @MockBean
    private TaskRepository taskRepository;

    @InjectMocks
    private final TaskService taskService;


    @Test
    void addTask() {
    }

    @Test
    void markTaskAsDone() {
    }

//    @Test
//    void deleteTask_ManagerDeletesTask_Success() {
//        // Arrange
//        UUID userId = UUID.randomUUID();
//        int taskId = 1;
//        Role MANAGER = new Role(1, "MANAGER");
//        Role USER = new Role(2, "USER");
//        Set<Role> authorities_manager = new HashSet<>();
//        Set<Role> authorities_user = new HashSet<>();
//        authorities_user.add(USER);
//        authorities_manager.add(MANAGER);
//        User manager = new User();
//        manager.setId(userId);
//        manager.setAuthorities(authorities_manager);
//
//        User user = User.builder().
//                authorities(authorities_user).
//                id(UUID.randomUUID()).
//                fistName("USER").
//                lastName("USER").
//                monthlyDeletionTokens(1).
//                build();
//
//        Task task = new Task();
//        task.setId(taskId);
//        task.setAssignedTo(user);
//        task.setAssignedBy(manager);
//
//        when(userRepository.findById(userId)).thenReturn(Optional.of(manager));
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//
//        // Act
//        assertDoesNotThrow(() -> taskService.deleteTaskForTokenDeletionRequest(userId, taskId));
//
//        // Assert
//        verify(taskRepository, times(1)).delete(task);
//        assertEquals(0, manager.getMonthlyDeletionTokens());  // Assert that the token is consumed
//    }

    @Test
    void updateTaskStatus_shouldMarkOverdueTasksAsNotDone() {
        Task task1 = new Task();
        task1.setId(1);
        Task task2 = new Task();
        task2.setId(2);
        List<Task> overdueTasks = new ArrayList<>(List.of(task2, task1));
        when(taskRepository.findOverdueTasks()).thenReturn(overdueTasks);
        Mockito.verify(taskRepository, Mockito.times(2)).save(Mockito.any(Task.class));


    }
}