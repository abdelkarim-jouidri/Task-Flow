package com.example.taskflow.Exceptions;

public class InvalidDueDateException extends RuntimeException {
    public InvalidDueDateException(String s) {
        super(s);
    }
}
