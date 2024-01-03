package com.example.taskflow.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime createdAt, ConstraintValidatorContext constraintValidatorContext) {
        if (createdAt == null) {
            return true;
        }


        return createdAt.isAfter(LocalDateTime.now()) || createdAt.isEqual(LocalDateTime.now());
    }
}
