package com.example.taskflow.Validations;

import com.example.taskflow.Entities.DTOs.Tag.TagDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Set;

public class MinimumOfThreeValidator implements ConstraintValidator<MinimumOfThree, Set<TagDTO>> {

    @Override
    public boolean isValid(Set<TagDTO> tagDTOS, ConstraintValidatorContext constraintValidatorContext) {
        return !tagDTOS.isEmpty() && tagDTOS.size()>=3;
    }
}
