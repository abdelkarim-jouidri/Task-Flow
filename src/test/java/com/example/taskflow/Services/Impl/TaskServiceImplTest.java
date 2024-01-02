package com.example.taskflow.Services.Impl;

import com.example.taskflow.Entities.Models.Role;
import com.example.taskflow.Entities.Models.Task;
import com.example.taskflow.Entities.Models.User;
import com.example.taskflow.Repositories.RoleRepository;
import com.example.taskflow.Repositories.TaskRepository;
import com.example.taskflow.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    public TaskServiceImplTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTask() {
    }

    @Test
    void markTaskAsDone() {
    }

    @Test
    void deleteTask_ManagerDeletesTask_Success() {
        // Arrange
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

        Task task = new Task();
        task.setId(taskId);
        task.setAssignedTo(user);
        task.setAssignedBy(manager);

        when(userRepository.findById(userId)).thenReturn(Optional.of(manager));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        assertDoesNotThrow(() -> taskService.deleteTask(userId, taskId));

        // Assert
        verify(taskRepository, times(1)).delete(task);
        assertEquals(0, manager.getMonthlyDeletionTokens());  // Assert that the token is consumed
    }
}