package com.example.taskflow.Entities.Models;

import com.example.taskflow.Entities.Enums.TaskStatus;
import com.example.taskflow.Validations.ValidStartDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "tasks")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "the description shouldn't be empty")
    private String description;
    @Future
    private LocalDate createdAt;
    @ValidStartDate
    private LocalDate startDate;

    private LocalDate dueDate;

    private TaskStatus taskStatus;
    @ManyToMany
    @JoinTable(
            name = "tasks_tags",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags ;

    @ManyToOne
    private User assignedTo;

    @ManyToOne
    private User assignedBy;

}
