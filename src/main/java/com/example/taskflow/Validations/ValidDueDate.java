package com.example.taskflow.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateValidator.class)
@Documented
public @interface ValidDueDate {
    String message() default "due date must be after the start date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
