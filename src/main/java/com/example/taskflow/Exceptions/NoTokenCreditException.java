package com.example.taskflow.Exceptions;

public class NoTokenCreditException extends RuntimeException {
    public NoTokenCreditException(String dailyChangeTokenLimitExceeded) {
        super(dailyChangeTokenLimitExceeded);
    }
}
