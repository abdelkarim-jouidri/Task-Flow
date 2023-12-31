package com.example.taskflow.Repositories;

import com.example.taskflow.Entities.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID uuid);

    @Query("SELECT u FROM User u JOIN u.authorities r WHERE r.authority = 'MANAGER' AND u.id = :userId")
    Optional<User> findUserWithRoleAdmin(@Param("userId") UUID userId);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN  u.tasks t " +
            "LEFT JOIN u.authorities a " +
            "WHERE a.authority='USER'" +
            "GROUP BY u.id ORDER BY COUNT(t.id) ASC LIMIT 1")
    Optional<User> findUserWithMinNumberOfTasks();
}
