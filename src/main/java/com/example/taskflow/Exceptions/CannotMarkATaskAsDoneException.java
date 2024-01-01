package com.example.taskflow.Exceptions;

public class CannotMarkATaskAsDoneException extends RuntimeException {
    public CannotMarkATaskAsDoneException(String s) {
        super(s);
    }
}
