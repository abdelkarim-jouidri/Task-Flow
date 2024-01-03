package com.example.taskflow.Repositories;

import com.example.taskflow.Entities.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.assignedTo IS NULL AND t.isReplaced = false")
    Optional<Task> findReplaceableNonAssignedTask(@Param("maxResults") int maxResults);
    default Optional<Task> findReplaceableNonAssignedTaskWithDefaultMaxResults() {
        return findReplaceableNonAssignedTask(1);
    }

    @Query("SELECT t FROM Task t WHERE t.dueDate<CURRENT_TIMESTAMP()")
    List<Task> findOverdueTasks();
}
