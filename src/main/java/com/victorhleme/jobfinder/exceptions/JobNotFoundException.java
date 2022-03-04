package com.victorhleme.jobfinder.exceptions;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String msg) {
        super(msg);
    }

    public JobNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}