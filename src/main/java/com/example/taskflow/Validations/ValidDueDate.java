package com.example.taskflow.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DueDateValidator.class)
@Documented
public @interface ValidDueDate {
    String message() default "Due date must be 3 days or more from today's date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
