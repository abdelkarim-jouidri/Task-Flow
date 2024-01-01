package com.example.taskflow.Exceptions;

public class DueDateIsInThePastException extends RuntimeException {
    public DueDateIsInThePastException() {
        super("Due date is expired, you cannot mark the task as DONE");
    }
}
