package com.rsreu.rodin.lab4.exceptions;

public class IncorrectCommandArgumentException extends Exception {

    public IncorrectCommandArgumentException() {
    }

    public IncorrectCommandArgumentException(String message) {
        super(message);
    }

    public IncorrectCommandArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCommandArgumentException(Throwable cause) {
        super(cause);
    }

    public IncorrectCommandArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
