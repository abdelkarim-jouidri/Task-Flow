package com.example.taskflow.Exceptions;

public class TokenRequestAlreadyConsumedException extends RuntimeException {
    public TokenRequestAlreadyConsumedException(String theTokenIsAlreadyConsumed) {
        super(theTokenIsAlreadyConsumed);
    }
}
