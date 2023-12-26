package com.example.taskflow.Repositories;

import com.example.taskflow.Entities.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
