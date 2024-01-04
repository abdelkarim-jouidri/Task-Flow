package com.example.taskflow.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StartDateValidator implements ConstraintValidator<ValidStartDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime startDate, ConstraintValidatorContext constraintValidatorContext) {
        if (startDate == null) {
            return true;
        }

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime minimumStartDate = currentDate.plus(3, ChronoUnit.DAYS);
//        currentdate<=startDate<=minu
//        return startDate.isAfter(minimumStartDate) || startDate.isEqual(minimumStartDate);
        return startDate.isBefore(minimumStartDate) && startDate.isAfter(currentDate);
    }
}
