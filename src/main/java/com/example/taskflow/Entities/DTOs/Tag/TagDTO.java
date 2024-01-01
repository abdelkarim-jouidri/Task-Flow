    package com.example.taskflow.Entities.DTOs.Tag;

    import jakarta.validation.constraints.NotEmpty;
    import lombok.Data;

    @Data
    public class TagDTO {
        @NotEmpty(message = "the name must not be empty")
        private String name;
        private Integer id;
    }
