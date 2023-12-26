package com.example.taskflow.Repositories;

import com.example.taskflow.Entities.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
