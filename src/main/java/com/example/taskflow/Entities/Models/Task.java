package com.example.taskflow.Entities.Models;

import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Validations.NotPastDate;
import com.example.taskflow.Validations.ValidStartDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "tasks")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "the description shouldn't be empty")
    private String description;
    private LocalDateTime createdAt;
//    @ValidStartDate @NotNull
    private LocalDateTime startDate;

    private LocalDateTime dueDate;

//    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private boolean isReplaced;
    @ManyToMany (cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "tasks_tags",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags ;

    @ManyToOne (cascade = CascadeType.MERGE)
    private User assignedTo;

    @ManyToOne (cascade = CascadeType.MERGE)
    private User assignedBy;

}
