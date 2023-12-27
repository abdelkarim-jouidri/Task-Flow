package com.example.taskflow.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumOfThreeValidator.class)
@Documented
public @interface MinimumOfThree {
    String message() default "You should select a minimum of 3 tags for the task";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
