package com.example.taskflow.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotPastDateValidator.class)
@Documented
public @interface NotPastDate {
    String message() default "created At must not be in the past";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
