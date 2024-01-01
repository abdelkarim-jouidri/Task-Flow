package com.example.taskflow.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartDateValidator.class)
@Documented
public @interface ValidStartDate {
    String message() default "start date must be 3 days or more from today's date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
