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

    @Query("SELECT u FROM User u JOIN u.authorities r WHERE r.authority = 'ADMIN' AND u.id = :userId")

    User findUserWithRoleAdmin(@Param("userId") UUID userId);
}
