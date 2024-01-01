package com.example.taskflow.Repositories;

import com.example.taskflow.Entities.Models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    boolean existsById(Integer id);
    Optional<Tag> findById(Integer id);
}
