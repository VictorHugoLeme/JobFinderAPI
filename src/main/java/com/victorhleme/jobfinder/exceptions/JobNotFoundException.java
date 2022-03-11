package com.victorhleme.jobfinder.exceptions;

import com.victorhleme.jobfinder.model.Job;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String msg) {
        super(msg);
    }

    public JobNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JobNotFoundException(Integer id) {
        super("Job not found! Id: "
                + id + ", type: "
                + Job.class);
    }
}