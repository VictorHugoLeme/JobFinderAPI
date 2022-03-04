package com.victorhleme.jobfinder.exceptions;

public class ConstraintException extends RuntimeException {

    public ConstraintException(String msg) {
        super(msg);
    }

    public ConstraintException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
