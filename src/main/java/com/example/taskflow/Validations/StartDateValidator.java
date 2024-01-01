package com.example.taskflow.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class StartDateValidator implements ConstraintValidator<ValidStartDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate dueDate, ConstraintValidatorContext constraintValidatorContext) {
        if(dueDate == null) return true;
        LocalDate currentDate = LocalDate.now();
        LocalDate minimumDueDate = currentDate.plusDays(3);

        return dueDate.equals(minimumDueDate) || dueDate.isAfter(minimumDueDate);
    }
}
