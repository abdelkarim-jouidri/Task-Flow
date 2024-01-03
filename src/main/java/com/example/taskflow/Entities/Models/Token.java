package com.example.taskflow.Entities.Models;

import com.example.taskflow.Entities.Enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor @NoArgsConstructor @Setter @Getter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "task_id", nullable = true)

    private Task task;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private LocalDateTime createdAt;
    private boolean isConsumed;

}
