package com.example.taskflow.Exceptions;

public class NonAdminUserCannotAssignATaskException extends RuntimeException {
    public NonAdminUserCannotAssignATaskException(String s) {
        super(s);
    }
}
