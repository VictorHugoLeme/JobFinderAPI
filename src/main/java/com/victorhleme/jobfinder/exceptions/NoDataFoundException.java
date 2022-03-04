package com.victorhleme.jobfinder.exceptions;

public class NoDataFoundException extends RuntimeException{

    public NoDataFoundException() {
        super("No object found!");
    }
    public NoDataFoundException(String msg) {
        super(msg);
    }
    public NoDataFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}